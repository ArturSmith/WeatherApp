package com.smith.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayWeatherDto(
    @SerializedName("avgtemp_c") val temp_c: Float,
    @SerializedName("condition") val conditionDto: ConditionDto
)
