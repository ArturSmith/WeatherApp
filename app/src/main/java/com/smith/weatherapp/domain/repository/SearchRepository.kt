package com.smith.weatherapp.domain.repository

import com.smith.weatherapp.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>

}