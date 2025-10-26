package com.example.weatherapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.ThemeState
import com.example.weatherapp.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    onToggleTheme: () -> Unit = {},
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val themeColor = colorResource(id = R.color.account_theme_color)
    val currentCity = weatherViewModel.currentCity
    var showDialog by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.change_password)) },
            text = {
                TextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text(stringResource(R.string.new_password)) }
                )
            },
            confirmButton = {
                Button(onClick = {
                    // Mock password change
                    showDialog = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.account), color = themeColor)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DirectionsWalk,
                            contentDescription = stringResource(R.string.go_to_welcome),
                            tint = themeColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (ThemeState.isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                            contentDescription = if (ThemeState.isDarkTheme)
                                stringResource(R.string.dark_mode)
                            else
                                stringResource(R.string.light_mode),
                            tint = themeColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(7.dp))
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.account),
                    tint = themeColor,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${currentCity.city}, ${currentCity.country}",
                        color = themeColor,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Temperature: ${currentCity.temperature}",
                    color = themeColor,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = currentCity.description,
                    color = themeColor,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Feels like ${currentCity.feelsLike}",
                    color = themeColor,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { showDialog = true }) {
                    Text(stringResource(R.string.change_password))
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.weather_api),
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountScreen() {
    MaterialTheme {
        AccountScreen(
            navController = rememberNavController(),
            onToggleTheme = {}
        )
    }
}
