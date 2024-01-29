package com.smith.weatherapp.domain.usecase

import com.smith.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

data class GetFavoriteCitiesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke() = repository.favoriteCities
}
