package com.example.musicapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicapp.presentation.favorites.FavoritesScreen
import com.example.musicapp.presentation.search.SearchScreen
import com.example.musicapp.presentation.trackdetail.TrackDetailScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Favorites,
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = currentDestination?.route in items.map { it.route }
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Search.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Search.route) {
                SearchScreen(onTrackClick = { trackId ->
                    navController.navigate("track_detail/$trackId")
                })
            }
            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(onTrackClick = { trackId ->
                    navController.navigate("track_detail/$trackId")
                })
            }
            composable(
                route = "track_detail/{trackId}",
                arguments = listOf(navArgument("trackId") { type = NavType.StringType })
            ) {
                TrackDetailScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}