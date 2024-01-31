package com.smith.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday") val dayDto: List<DayDto>
)
