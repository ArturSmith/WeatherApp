package com.smith.weatherapp.domain.usecase

import com.smith.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

data class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityId:Int) = repository.getWeather(cityId)
}
