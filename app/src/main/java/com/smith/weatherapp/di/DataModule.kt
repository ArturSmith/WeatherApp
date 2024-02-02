package com.smith.weatherapp.di

import android.content.Context
import com.smith.weatherapp.data.local.db.FavoriteCitiesDao
import com.smith.weatherapp.data.local.db.FavoriteDatabase
import com.smith.weatherapp.data.network.api.ApiFactory
import com.smith.weatherapp.data.network.api.ApiService
import com.smith.weatherapp.data.repository.FavoriteRepositoryImpl
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
    fun favoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

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
        fun provideFavoriteDatabase(context: Context): FavoriteDatabase {
            return FavoriteDatabase.getInstance(context)
        }

        @ApplicationScope
        @Provides
        fun provideFavoriteDao(database: FavoriteDatabase): FavoriteCitiesDao {
            return database.FavoriteCitiesDao()
        }
    }
}