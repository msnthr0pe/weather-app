package com.example.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.MockCityWeather

class WeatherViewModel : ViewModel() {
    val cities = listOf(
        MockCityWeather("Moscow", "RU", "7°C", "Clear sky", "Feels like 2°C"),
        MockCityWeather("London", "UK", "12°C", "Cloudy", "Feels like 10°C"),
        MockCityWeather("Tokyo", "JP", "18°C", "Sunny", "Feels like 17°C"),
        MockCityWeather("New York", "US", "14°C", "Rainy", "Feels like 12°C"),
        MockCityWeather("Paris", "FR", "9°C", "Fog", "Feels like 7°C")
    )

    var currentIndex by mutableStateOf(0)
        private set

    val currentCity: MockCityWeather
        get() = cities[currentIndex]

    fun onPreviousCity() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else cities.lastIndex
    }

    fun onNextCity() {
        currentIndex = if (currentIndex < cities.lastIndex) currentIndex + 1 else 0
    }
}
