package com.example.musicapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favorites")
}
