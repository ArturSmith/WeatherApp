package com.smith.weatherapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smith.weatherapp.data.local.models.CityDbModel

@Database(entities = [CityDbModel::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase() : RoomDatabase() {

    abstract fun FavoriteCitiesDao(): FavoriteCitiesDao
    companion object {
        private const val NAME = "FavoriteDatabase"
        private var INSTANCE: FavoriteDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): FavoriteDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = FavoriteDatabase::class.java,
                    name = NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}