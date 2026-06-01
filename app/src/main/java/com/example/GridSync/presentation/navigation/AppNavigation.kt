package com.example.GridSync.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.GridSync.presentation.dashboard.DashboardScreen
import com.example.GridSync.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {

        composable(AppRoutes.SPLASH) {
            SplashScreen(navController)
        }

        composable(AppRoutes.DASHBOARD) {
            DashboardScreen()
        }
    }
}