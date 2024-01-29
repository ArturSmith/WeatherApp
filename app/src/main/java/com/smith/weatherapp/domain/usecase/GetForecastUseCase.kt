package com.smith.weatherapp.domain.usecase

import com.smith.weatherapp.domain.repository.FavoriteRepository
import com.smith.weatherapp.domain.repository.SearchRepository
import com.smith.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

data class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityId:Int) = repository.getForecast(cityId)
}
