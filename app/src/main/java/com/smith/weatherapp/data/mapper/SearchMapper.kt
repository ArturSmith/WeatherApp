package com.smith.weatherapp.data.mapper

import com.smith.weatherapp.data.network.dto.CityDto
import com.smith.weatherapp.domain.entity.City

fun CityDto.toEntity() = City(id, name, country)
fun List<CityDto>.toEntities() = map { it.toEntity() }