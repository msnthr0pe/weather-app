// data/repository/WeatherRepository.kt

package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.LocationResponse
import com.example.weatherapp.data.api.RetrofitClient

class WeatherRepository {
    suspend fun searchCities(city: String, apiKey: String): List<LocationResponse> {
        // Если нужно, можно отлавливать Exception и обрабатывать отдельно
        return RetrofitClient.api.searchCities(city, limit = 10, apiKey = apiKey)
    }
}
