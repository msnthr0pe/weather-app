package com.example.weatherapp.data.api

import com.google.gson.annotations.SerializedName

/**
 * Ответ от /geo/1.0/direct
 * Документация: https://openweathermap.org/api/geocoding-api
 */
data class LocationResponse(
    val name: String,
    @SerializedName("local_names") val localNames: LocalNames? = null,
    val country: String,
    val state: String? = null,
    val lat: Double,
    val lon: Double
) {
    val russianName: String
        get() = localNames?.ru ?: name
}

data class LocalNames(
    val ru: String? = null
)

/**
 * Ответ от /data/2.5/weather
 * Документация: https://openweathermap.org/current#current_JSON
 */
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double
)

data class Weather(
    val description: String
)
