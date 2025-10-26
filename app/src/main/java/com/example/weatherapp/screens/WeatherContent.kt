package com.example.weatherapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

data class MockCityWeather(
    val city: String,
    val country: String,
    val temperature: String,
    val description: String,
    val feelsLike: String
)

@Composable
fun WeatherContent(navController: NavHostController) {
    val cities = listOf(
        MockCityWeather("Moscow", "RU", "7°C", "Clear sky", "Feels like 2°C"),
        MockCityWeather("London", "UK", "12°C", "Cloudy", "Feels like 10°C"),
        MockCityWeather("Tokyo", "JP", "18°C", "Sunny", "Feels like 17°C"),
        MockCityWeather("New York", "US", "14°C", "Rainy", "Feels like 12°C"),
        MockCityWeather("Paris", "FR", "9°C", "Fog", "Feels like 7°C")
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentCity = cities[currentIndex]

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Город и страна
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${currentCity.city}, ${currentCity.country}",
                color = Color(0xFF6E4A5C),
                style = MaterialTheme.typography.displayMedium
            )
        }

        // Центральный блок
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Температура и стрелки
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            currentIndex = if (currentIndex > 0) currentIndex - 1 else cities.lastIndex
                        },
                        modifier = Modifier.size(30.dp),
                        enabled = true
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Previous city",
                            tint = Color(0xFF6E4A5C),
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier.width(204.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentCity.temperature,
                            color = Color(0xFF6E4A5C),
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            currentIndex = if (currentIndex < cities.lastIndex) currentIndex + 1 else 0
                        },
                        modifier = Modifier.size(30.dp),
                        enabled = true
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Next city",
                            tint = Color(0xFF6E4A5C),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                // Описание погоды
                Text(
                    text = currentCity.description,
                    color = Color(0xFF6E4A5C),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                // Feels like
                Text(
                    text = currentCity.feelsLike,
                    color = Color(0xFF6E4A5C),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                // Кнопка "more info"
                OutlinedButton(
                    onClick = {
                        navController.navigate(
                            "account_screen/${currentCity.city}/${currentCity.country}/${currentCity.temperature}/${currentCity.description}/${currentCity.feelsLike}"
                        )
                    },
                    modifier = Modifier
                        .width(185.dp)
                        .height(40.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF6E4A5C)),
                    enabled = true
                ) {
                    Text(text = "more info", color = Color(0xFF6E4A5C))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    MaterialTheme {
        WeatherContent(navController = rememberNavController())
    }
}
