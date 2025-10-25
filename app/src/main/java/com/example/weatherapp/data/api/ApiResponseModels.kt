package com.example.weatherapp.data.api

/**
 * Ответ от /geo/1.0/direct
 * Документация: https://openweathermap.org/api/geocoding-api
 */
data class LocationResponse(
    val name: String,
    val country: String,
    val state: String? = null,
//    val lat: Double,
//    val lon: Double
)
