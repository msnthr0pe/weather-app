package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.LocationResponse
import com.example.weatherapp.data.api.RetrofitClient
import com.example.weatherapp.data.api.WeatherResponse

class WeatherRepository {
    private val api = RetrofitClient.api

    suspend fun searchCities(city: String, apiKey: String): List<LocationResponse> {
        return api.searchCities(city, limit = 10, apiKey = apiKey)
    }

    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse {
        return api.getWeather(lat, lon, apiKey)
    }
}
