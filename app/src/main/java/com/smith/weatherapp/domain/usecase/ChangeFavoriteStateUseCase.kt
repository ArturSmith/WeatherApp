package com.smith.weatherapp.domain.usecase

import com.smith.weatherapp.domain.entity.City
import com.smith.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

data class ChangeFavoriteStateUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun addToFavorite(city: City) = repository.addToFavorite(city)
    suspend fun removeFromFavorite(cityId:Int) = repository.removeFromFavorite(cityId)
}
