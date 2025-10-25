package com.example.weatherapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    /**
     * Эндпоинт для поиска городов по названию:
     * https://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=API_KEY
     */
    @GET("geo/1.0/direct")
    suspend fun searchCities(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") apiKey: String
    ): List<LocationResponse>
}
