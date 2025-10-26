package com.example.weatherapp.screens

import WeatherDetailsScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.weatherapp.BottomNavItem
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.ThemeState

@Composable
fun MainScreen(navController: NavHostController) {

    val onThemeChange = { ThemeState.isDarkTheme = !ThemeState.isDarkTheme }

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { WeatherBottomNavigation(bottomNavController) }
    ) { innerPadding ->

        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Weather.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Главный экран с погодой
            composable(BottomNavItem.Weather.route) {
                WeatherContent(navController = bottomNavController)
            }

            // Экран поиска
            composable(BottomNavItem.Search.route) {
                SearchScreen(bottomNavController)
            }

            // Экран профиля (account)
            composable(BottomNavItem.Profile.route) {
                AccountScreen(
                    navController = navController,
                    city = "Paris",
                    country = "FR",
                    temperature = "9°C",
                    description = "Fog",
                    feelsLike = "Feels like 7°C",
                    onToggleTheme = onThemeChange
                )
            }

            // Новый маршрут — переход с MainScreen при нажатии "more info"
            composable(
                "weather_details_screen/{city}/{country}/{temperature}/{description}/{feelsLike}"
            ) { backStackEntry ->
                val city = backStackEntry.arguments?.getString("city") ?: ""
                val country = backStackEntry.arguments?.getString("country") ?: ""
                val temperature = backStackEntry.arguments?.getString("temperature") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                val feelsLike = backStackEntry.arguments?.getString("feelsLike") ?: ""

                WeatherDetailsScreen(
                    navController = bottomNavController,
                    city = city,
                    country = country,
                    temperature = temperature,
                    description = description,
                    feelsLike = feelsLike
                )
            }
        }
    }
}

@Composable
fun WeatherBottomNavigation(navController: NavHostController) {
    val items = listOf(BottomNavItem.Weather, BottomNavItem.Search, BottomNavItem.Profile)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.Transparent
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route ||
                    (item.route == BottomNavItem.Weather.route && currentRoute?.startsWith("weather_details_screen") == true)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.bottom_nav_selected),
                    unselectedIconColor = colorResource(id = R.color.bottom_nav_unselected),
                    indicatorColor = colorResource(id = R.color.bottom_nav_background)
                ),
                alwaysShowLabel = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen(navController = rememberNavController())
    }
}
