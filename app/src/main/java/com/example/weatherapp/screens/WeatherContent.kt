package com.example.weatherapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun WeatherContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Верхняя область: город и страна
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Moscow, RU",
                color = Color(0xFF6E4A5C),
                style = MaterialTheme.typography.displayMedium
            )
        }

        // Центральный блок с информацией о погоде, расположенный по центру вертикали
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
                // Блок с температурой и стрелками
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        androidx.compose.material3.IconButton(
                            onClick = { /* TODO: Предыдущая локация */ },
                            modifier = Modifier.size(30.dp),
                            enabled = true
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Previous location",
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
                                text = "7°С",
                                color = Color(0xFF6E4A5C),
                                style = MaterialTheme.typography.displayLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.material3.IconButton(
                            onClick = { /* TODO: Следующая локация */ },
                            modifier = Modifier.size(30.dp),
                            enabled = true
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Next location",
                                tint = Color(0xFF6E4A5C),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                // Описание погоды
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Clear sky",
                        color = Color(0xFF6E4A5C),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }

                // Feels like
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Feels like 2°",
                        color = Color(0xFF6E4A5C),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

                // Outlined-кнопка "more info" с закруглёнными углами
                OutlinedButton(
                    onClick = {
                        navController.navigate("weather_info")
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