package com.smith.weatherapp.data.mapper

import com.smith.weatherapp.data.local.models.CityDbModel
import com.smith.weatherapp.domain.entity.City

fun City.toDbModel() = CityDbModel(id, name, country)
fun CityDbModel.toModel() = City(id, name, country)
fun List<CityDbModel>.toListOfModels() = map { it.toModel() }
fun List<City>.toListOfDbModels() = map { it.toDbModel() }