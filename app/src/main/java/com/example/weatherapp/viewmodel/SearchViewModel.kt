// viewmodel/SearchViewModel.kt

package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.LocationResponse
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val apiKey = "20f4c7cb2f77ea40affe9cf3b27f38cc"

    // Храним состояние экрана (Loading, Success, Error)
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(emptyList()))
    val uiState: StateFlow<SearchUiState> = _uiState

    private var lastQuery: String = ""  // чтобы повторять запрос при «Обновить»

    fun searchCities(query: String) {
        // Сохраняем запрос, чтобы можно было «Обновить»
        lastQuery = query

        // Запускаем корутину для запроса
        viewModelScope.launch {
            // Пока грузим
            _uiState.value = SearchUiState.Loading

            // Если поле пустое — возвращаем пустой список
            if (query.isBlank()) {
                _uiState.value = SearchUiState.Success(emptyList())
                return@launch
            }

            try {
                val result = repository.searchCities(query, apiKey)
                // Если пусто — показываем плейсхолдер «нет результатов»
                if (result.isEmpty()) {
                    _uiState.value = SearchUiState.Error("No results found")
                } else {
                    _uiState.value = SearchUiState.Success(result)
                }
            } catch (e: Exception) {
                // При ошибке: _uiState = Error(...)
                _uiState.value = SearchUiState.Error("Error occurred: ${e.message}")
            }
        }
    }

    // Повторяем запрос с тем же lastQuery
    fun retryLastQuery() {
        searchCities(lastQuery)
    }
}

// Состояния экрана
sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val data: List<LocationResponse>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
