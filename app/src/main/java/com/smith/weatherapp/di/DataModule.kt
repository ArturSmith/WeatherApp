package com.smith.weatherapp.di

import android.content.Context
import com.smith.weatherapp.data.local.db.FavouriteCitiesDao
import com.smith.weatherapp.data.local.db.FavouriteDatabase
import com.smith.weatherapp.data.network.api.ApiFactory
import com.smith.weatherapp.data.network.api.ApiService
import com.smith.weatherapp.data.repository.FavouriteRepositoryImpl
import com.smith.weatherapp.data.repository.SearchRepositoryImpl
import com.smith.weatherapp.data.repository.WeatherRepositoryImpl
import com.smith.weatherapp.domain.repository.FavoriteRepository
import com.smith.weatherapp.domain.repository.SearchRepository
import com.smith.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun favoriteRepository(impl: FavouriteRepositoryImpl): FavoriteRepository

    @ApplicationScope
    @Binds
    fun weatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @ApplicationScope
    @Binds
    fun searchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @ApplicationScope
        @Provides
        fun provideFavoriteDatabase(context: Context): FavouriteDatabase {
            return FavouriteDatabase.getInstance(context)
        }

        @ApplicationScope
        @Provides
        fun provideFavoriteDao(database: FavouriteDatabase): FavouriteCitiesDao {
            return database.FavoriteCitiesDao()
        }
    }
}