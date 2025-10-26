package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.LocationResponse
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val apiKey = "20f4c7cb2f77ea40affe9cf3b27f38cc"

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Success(emptyList()))
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf<SearchUiState>(SearchUiState.Success(emptyList()))
                    } else {
                        flow {
                            emit(SearchUiState.Loading)
                            try {
                                val result = repository.searchCities(query, apiKey)
                                if (result.isEmpty()) {
                                    emit(SearchUiState.Error("No results found"))
                                } else {
                                    emit(SearchUiState.Success(result))
                                }
                            } catch (e: Exception) {
                                emit(SearchUiState.Error("Error occurred: ${e.message}"))
                            }
                        }
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun retryLastQuery() {
        onSearchQueryChanged(_searchQuery.value)
    }
}

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val data: List<LocationResponse>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
