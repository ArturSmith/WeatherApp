package com.smith.weatherapp.domain.usecase

import com.smith.weatherapp.domain.repository.FavoriteRepository
import com.smith.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

data class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query:String) = repository.search(query)
}
