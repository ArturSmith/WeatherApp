package com.smith.weatherapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smith.weatherapp.data.local.models.CityDbModel

@Database(entities = [CityDbModel::class], version = 1, exportSchema = false)
abstract class FavouriteDatabase() : RoomDatabase() {

    abstract fun FavoriteCitiesDao(): FavouriteCitiesDao
    companion object {
        private const val NAME = "FavoriteDatabase"
        private var INSTANCE: FavouriteDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): FavouriteDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = FavouriteDatabase::class.java,
                    name = NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}