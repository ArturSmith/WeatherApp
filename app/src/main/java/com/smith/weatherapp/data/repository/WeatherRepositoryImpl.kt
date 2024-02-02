package com.smith.weatherapp.data.repository

import com.smith.weatherapp.data.mapper.toEntity
import com.smith.weatherapp.data.network.api.ApiService
import com.smith.weatherapp.domain.entity.Forecast
import com.smith.weatherapp.domain.entity.Weather
import com.smith.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int) =
        apiService.loadCurrentWeather(query = "$PREFIX_CITY_ID$cityId").toEntity()

    override suspend fun getForecast(cityId: Int): Forecast =
        apiService.loadForecast(query = "$PREFIX_CITY_ID$cityId").toEntity()

    private companion object {
        const val PREFIX_CITY_ID = "id:"
    }
}