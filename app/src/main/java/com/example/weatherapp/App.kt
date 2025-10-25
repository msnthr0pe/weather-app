package com.example.weatherapp

import SignUpScreen
import WeatherDetailsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.AccountScreen
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.screens.SearchScreen
import com.example.weatherapp.screens.SignInScreen
import com.example.weatherapp.screens.WelcomeScreen
import com.example.weatherapp.ui.theme.ThemeState

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val onToggleTheme = { ThemeState.isDarkTheme = !ThemeState.isDarkTheme }

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("signIn") {
            SignInScreen(navController)
        }
        composable("signUp") {
            SignUpScreen(navController)
        }
        composable("home") {
            MainScreen(
                navController = navController
            )
        }
        composable("weatherDetails") {
            WeatherDetailsScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("account") {
            AccountScreen(
                navController = navController,
                onToggleTheme = onToggleTheme
            )
        }
    }
}
