package com.example.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.LocationResponse
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.model.MockCityWeather
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val apiKey = "20f4c7cb2f77ea40affe9cf3b27f38cc"

    private val _cities = listOf(
        MockCityWeather("Москва", "RU", "7°C", "Чистое небо", "Ощущается как 2°C"),
        MockCityWeather("Лондон", "UK", "12°C", "Облачно", "Ощущается как 10°C"),
        MockCityWeather("Токио", "JP", "18°C", "Солнечно", "Ощущается как 17°C"),
        MockCityWeather("Нью-Йорк", "US", "14°C", "Дождливо", "Ощущается как 12°C"),
        MockCityWeather("Париж", "FR", "9°C", "Туман", "Ощущается как 7°C")
    ).toMutableStateList()

    val cities: List<MockCityWeather> = _cities

    var currentIndex by mutableStateOf(0)
        private set

    val currentCity: MockCityWeather
        get() = if (cities.isNotEmpty() && currentIndex in cities.indices) cities[currentIndex] else MockCityWeather(
            "",
            "",
            "",
            "",
            ""
        )

    fun onPreviousCity() {
        if (cities.isNotEmpty()) {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else cities.lastIndex
        }
    }

    fun onNextCity() {
        if (cities.isNotEmpty()) {
            currentIndex = if (currentIndex < cities.lastIndex) currentIndex + 1 else 0
        }
    }

    fun addCity(location: LocationResponse) {
        val cityExists = _cities.any { it.city.equals(location.russianName, ignoreCase = true) }
        if (cityExists) {
            return
        }

        viewModelScope.launch {
            try {
                val weatherResponse = repository.getWeather(location.lat, location.lon, apiKey)
                val newCity = MockCityWeather(
                    city = location.russianName,
                    country = location.country,
                    temperature = "${weatherResponse.main.temp.toInt()}°C",
                    description = weatherResponse.weather.firstOrNull()?.description ?: "",
                    feelsLike = "Ощущается как ${weatherResponse.main.feelsLike.toInt()}°C"
                )
                _cities.add(newCity)
                currentIndex = _cities.lastIndex
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun removeCity(city: String) {
        val indexToRemove = _cities.indexOfFirst { it.city.equals(city, ignoreCase = true) }
        if (indexToRemove != -1) {
            _cities.removeAt(indexToRemove)
            if (currentIndex >= indexToRemove && currentIndex > 0) {
                currentIndex--
            } else if (_cities.isEmpty()) {
                currentIndex = 0
            }
        }
    }
}
