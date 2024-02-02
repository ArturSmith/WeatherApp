package com.smith.weatherapp.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.smith.weatherapp.domain.entity.City
import com.smith.weatherapp.domain.entity.Forecast
import com.smith.weatherapp.domain.usecase.ChangeFavoriteStateUseCase
import com.smith.weatherapp.domain.usecase.GetForecastUseCase
import com.smith.weatherapp.domain.usecase.ObserveFavoriteStateUseCase
import com.smith.weatherapp.presentation.details.DetailsStore.Intent
import com.smith.weatherapp.presentation.details.DetailsStore.Label
import com.smith.weatherapp.presentation.details.DetailsStore.State
import com.smith.weatherapp.presentation.search.SearchStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data object ClickAddToFavorite : Intent
    }

    data class State(
        val city: City,
        val isFavorite: Boolean,
        val forecastState: ForecastState
    ) {
        sealed interface ForecastState {
            data object Initial : ForecastState
            data object Loading : ForecastState
            data object Error : ForecastState
            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }

    sealed interface Label {
        data object ClickBack : Label

    }
}

class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
    private val observeFavoriteStateUseCase: ObserveFavoriteStateUseCase
) {

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                city = city,
                isFavorite = false,
                forecastState = State.ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavoriteStatusChanged(val isFavourite: Boolean) : Action
        data class ForecastLoaded(val forecast: Forecast) : Action
        data object ForecastStartLoading : Action
        data object ForecastLoadingError : Action
    }

    private sealed interface Msg {
        data class FavoriteStatusChanged(val isFavourite: Boolean) : Msg
        data class ForecastLoaded(val forecast: Forecast) : Msg
        data object ForecastStartLoading : Msg
        data object ForecastLoadingError : Msg
    }

    private inner class BootstrapperImpl(val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavoriteStateUseCase(city.id).collect {
                    dispatch(Action.FavoriteStatusChanged(it))
                }
            }
            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickAddToFavorite -> {
                    val state = getState()
                    scope.launch {
                        if (state.isFavorite) {
                            changeFavoriteStateUseCase.removeFromFavorite(state.city.id)
                        } else {
                            changeFavoriteStateUseCase.addToFavorite(state.city)
                        }
                    }
                }

                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavoriteStatusChanged -> {
                    dispatch(Msg.FavoriteStatusChanged(action.isFavourite))
                }

                is Action.ForecastLoaded -> {
                    dispatch(Msg.ForecastLoaded(action.forecast))
                }

                Action.ForecastLoadingError -> {
                    dispatch(Msg.ForecastLoadingError)
                }

                Action.ForecastStartLoading -> {
                    dispatch(Msg.ForecastStartLoading)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.FavoriteStatusChanged -> {
                copy(isFavorite = message.isFavourite)
            }

            is Msg.ForecastLoaded -> {
                copy(forecastState = State.ForecastState.Loaded(message.forecast))
            }

            Msg.ForecastLoadingError -> {
                copy(forecastState = State.ForecastState.Error)
            }

            Msg.ForecastStartLoading -> {
                copy(forecastState = State.ForecastState.Loading)
            }
        }
    }
}
