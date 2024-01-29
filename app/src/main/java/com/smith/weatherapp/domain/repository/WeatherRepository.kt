package com.smith.weatherapp.domain.repository

import com.smith.weatherapp.domain.entity.Forecast
import com.smith.weatherapp.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast

}