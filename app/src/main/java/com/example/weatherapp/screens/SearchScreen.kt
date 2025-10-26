package com.example.weatherapp.screens

import LocationCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.BottomNavItem
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.SearchUiState
import com.example.weatherapp.viewmodel.SearchViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    searchViewModel: SearchViewModel = viewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var query by remember { mutableStateOf("") }

    val uiState by searchViewModel.uiState.collectAsState()
    val history by searchViewModel.searchHistory.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            searchViewModel.clearSearch()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                searchViewModel.onSearchQueryChanged(it)
            },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_placeholder),
                    color = colorResource(id = R.color.app_theme_color)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            ),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            query = ""
                            searchViewModel.onSearchQueryChanged("")
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_text)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon_desc)
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is SearchUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.app_theme_color)
                    )
                }
            }

            is SearchUiState.Success -> {
                if (state.data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { location ->
                            LocationCard(
                                city = location.russianName,
                                country = location.country,
                                state = location.state ?: "Unknown",
                                onAddClick = {
                                    weatherViewModel.addCity(location)
                                    navController.navigate(BottomNavItem.Weather.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                } else if (query.isBlank() && history.isNotEmpty()) {
                    SearchHistoryList(history = history) { historyQuery ->
                        query = historyQuery
                        searchViewModel.onSearchQueryChanged(historyQuery)
                    }
                } else if (query.isNotBlank()) {
                    NoResultsPlaceholder()
                }
            }

            is SearchUiState.Error -> {
                val errorMessage = state.message
                ErrorPlaceholder(
                    message = errorMessage,
                    onRetry = { searchViewModel.retryLastQuery() }
                )
            }
        }
    }
}

@Composable
fun SearchHistoryList(history: List<String>, onItemClick: (String) -> Unit) {
    Text("История поиска", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn {
        items(history) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(it) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.History, contentDescription = "History icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(it)
            }
        }
    }
}

@Composable
fun NoResultsPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.no_results_found))
    }
}

@Composable
fun ErrorPlaceholder(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh_desc)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.refresh))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    MaterialTheme {
        SearchScreen(
            navController = rememberNavController(),
            weatherViewModel = viewModel()
        )
    }
}
