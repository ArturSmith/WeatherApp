package com.smith.weatherapp.data.repository

import com.smith.weatherapp.data.local.db.FavouriteCitiesDao
import com.smith.weatherapp.data.mapper.toDbModel
import com.smith.weatherapp.data.mapper.toListOfModels
import com.smith.weatherapp.domain.entity.City
import com.smith.weatherapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteCitiesDao: FavouriteCitiesDao
) : FavoriteRepository {
    override val favoriteCities: Flow<List<City>> = favouriteCitiesDao.getFavoriteCities()
        .map {
            it.toListOfModels()
        }

    override fun observeIsFavorite(cityId: Int)= favouriteCitiesDao.observeIfFavorite(cityId)

    override suspend fun addToFavorite(city: City) {
        favouriteCitiesDao.addFavoriteCity(city.toDbModel())
    }

    override suspend fun removeFromFavorite(cityId: Int) {
        favouriteCitiesDao.removeFromFavorites(cityId)
    }
}