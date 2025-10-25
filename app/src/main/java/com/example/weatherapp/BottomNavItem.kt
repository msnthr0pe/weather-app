package com.example.weatherapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {

    object Weather : BottomNavItem(
        route = "weather",
        icon = Icons.Filled.LocationOn,
        label = "Weather"
    )

    object Search : BottomNavItem(
        route = "search",
        icon = Icons.Filled.Search,
        label = "Search"
    )

    object Profile : BottomNavItem(
        route = "profile",
        icon = Icons.Filled.Person,
        label = "Profile"
    )
}
