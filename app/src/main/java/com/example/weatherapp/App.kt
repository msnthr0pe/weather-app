package com.example.weatherapp

import SignUpScreen
import WeatherDetailsScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.AccountScreen
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.screens.SearchScreen
import com.example.weatherapp.screens.SignInScreen
import com.example.weatherapp.screens.WelcomeScreen
import com.example.weatherapp.ui.theme.ThemeState
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val onToggleTheme = { ThemeState.isDarkTheme = !ThemeState.isDarkTheme }
    val weatherViewModel: WeatherViewModel = viewModel()

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
                navController = navController,
                weatherViewModel = weatherViewModel
            )
        }
        composable("weatherDetails/{city}/{country}/{temperature}/{description}/{feelsLike}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            val country = backStackEntry.arguments?.getString("country") ?: ""
            val temperature = backStackEntry.arguments?.getString("temperature") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val feelsLike = backStackEntry.arguments?.getString("feelsLike") ?: ""

            WeatherDetailsScreen(
                navController = navController,
                weatherViewModel = weatherViewModel,
                city = city,
                country = country,
                temperature = temperature,
                description = description,
                feelsLike = feelsLike
            )
        }
        composable("search") {
            SearchScreen(
                navController = navController,
                weatherViewModel = weatherViewModel
            )
        }
    }
}
