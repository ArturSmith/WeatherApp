package com.smith.weatherapp.presentation.favourite

import com.smith.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {
    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun onCLickAddFavourite()

    fun onCityItemClick(city: City)
}