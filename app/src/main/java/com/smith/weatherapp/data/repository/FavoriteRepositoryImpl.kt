package com.smith.weatherapp.data.repository

import com.smith.weatherapp.data.local.db.FavoriteCitiesDao
import com.smith.weatherapp.data.local.db.FavoriteDatabase
import com.smith.weatherapp.data.mapper.toDbModel
import com.smith.weatherapp.data.mapper.toListOfModels
import com.smith.weatherapp.domain.entity.City
import com.smith.weatherapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCitiesDao: FavoriteCitiesDao
) : FavoriteRepository {
    override val favoriteCities: Flow<List<City>> = favoriteCitiesDao.getFavoriteCities()
        .map {
            it.toListOfModels()
        }

    override fun observeIsFavorite(cityId: Int)= favoriteCitiesDao.observeIfFavorite(cityId)

    override suspend fun addToFavorite(city: City) {
        favoriteCitiesDao.addFavoriteCity(city.toDbModel())
    }

    override suspend fun removeFromFavorite(cityId: Int) {
        favoriteCitiesDao.removeFromFavorites(cityId)
    }
}