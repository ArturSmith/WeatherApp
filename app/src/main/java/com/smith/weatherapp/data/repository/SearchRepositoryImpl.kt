package com.smith.weatherapp.data.repository

import com.smith.weatherapp.data.mapper.toEntities
import com.smith.weatherapp.data.mapper.toEntity
import com.smith.weatherapp.data.network.api.ApiService
import com.smith.weatherapp.domain.entity.City
import com.smith.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> =
        apiService.searchCity(query).toEntities()
}