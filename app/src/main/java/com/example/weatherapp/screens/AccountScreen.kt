package com.example.weatherapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    onToggleTheme: (Boolean) -> Unit
) {
    val themeColor = colorResource(id = R.color.account_theme_color)

    var isDarkMode by rememberSaveable { mutableStateOf(false) }

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
                            navController.navigate("welcome") {
                                popUpTo(navController.graph.findStartDestination().id)
                            }
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
                    IconButton(
                        onClick = {
                            isDarkMode = !isDarkMode
                            onToggleTheme(isDarkMode)
                        }
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                            contentDescription = if (isDarkMode) stringResource(R.string.dark_mode) else stringResource(R.string.light_mode),
                            tint = themeColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
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
                Spacer(modifier = Modifier.height(7.dp))

                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.hello_user),
                        color = themeColor,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = stringResource(R.string.track_info),
                    color = themeColor,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
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
