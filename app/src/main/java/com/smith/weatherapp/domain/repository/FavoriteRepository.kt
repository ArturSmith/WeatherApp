package com.smith.weatherapp.domain.repository

import com.smith.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {

    val favoriteCities: Flow<List<City>>
    fun observeIsFavorite(cityId: Int): Flow<Boolean>
    suspend fun addToFavorite(city: City)
    suspend fun removeFromFavorite(cityId: Int)


}