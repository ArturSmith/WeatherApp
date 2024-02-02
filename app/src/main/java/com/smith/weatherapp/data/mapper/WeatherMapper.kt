package com.smith.weatherapp.data.mapper

import com.smith.weatherapp.data.network.dto.DayDto
import com.smith.weatherapp.data.network.dto.WeatherCurrentDto
import com.smith.weatherapp.data.network.dto.WeatherDto
import com.smith.weatherapp.data.network.dto.WeatherForecastDto
import com.smith.weatherapp.domain.entity.Forecast
import com.smith.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity(): Weather = weather.toEntity()
fun WeatherDto.toEntity() = Weather(
    tempC = tempC,
    conditionText = condition.text,
    date = date.toCalendar(),
    conditionUrl = condition.iconUrl.correctImageUrl()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecastDto.dayDto.drop(1).map { dayDto ->
        val dayWeatherDto = dayDto.day
        Weather(
            tempC = dayWeatherDto.temp_c,
            conditionText = dayWeatherDto.conditionDto.text,
            conditionUrl = dayWeatherDto.conditionDto.iconUrl.correctImageUrl(),
            date = dayDto.date.toCalendar()
        )
    }
)


private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() =
    "https:$this".replace(oldValue = "64x64", newValue = "128x128")

