package com.smith.weatherapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smith.weatherapp.data.local.models.CityDbModel
import kotlinx.coroutines.flow.Flow
@Dao
interface FavouriteCitiesDao {

    @Query("SELECT * FROM favourite_cities")
    fun getFavoriteCities(): Flow<List<CityDbModel>>
    @Query("SELECT EXISTS (SELECT * FROM favourite_cities WHERE id=:cityId LIMIT 1)")
    fun observeIfFavorite(cityId:Int) : Flow<Boolean>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(cityDbModel: CityDbModel)
    @Query("DELETE FROM favourite_cities WHERE id=:cityId")
    suspend fun removeFromFavorites(cityId:Int)
}