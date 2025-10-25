package com.example.weatherapp.screens

import WeatherDetailsScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.weatherapp.BottomNavItem
import com.example.weatherapp.R
import com.example.weatherapp.WeatherContent

@Composable
fun MainScreen(navController: NavHostController = rememberNavController(), onToggleTheme: () -> Unit) {
    var isDarkMode by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = { WeatherBottomNavigation(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Weather.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Weather.route) { WeatherContent(navController) }
            composable(BottomNavItem.Search.route) { SearchScreen(navController) }
            composable(BottomNavItem.Profile.route) {
                AccountScreen(
                    navController = navController,
                    onToggleTheme = { isDarkMode = it }
                )
            }
            composable("weather_info") { WeatherDetailsScreen(navController) }
            composable("welcome") { WelcomeScreen(navController) }
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
            val isSelected = currentRoute == item.route || (item.route == BottomNavItem.Weather.route && currentRoute == "weather_info")

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
        MainScreen(onToggleTheme = {})
    }
}
