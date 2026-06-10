package com.example.GridSync.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.GridSync.presentation.dashboard.DashboardScreen
import com.example.GridSync.presentation.dsm.DSMScreen
import com.example.GridSync.presentation.dsm.generalsellerdsm.GeneralSellerDsmScreen
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
            DashboardScreen(
                onDsmClick = {
                    if (navController.currentDestination?.route == AppRoutes.DASHBOARD) {
                        navController.navigate(AppRoutes.DSM)
                    }
                }
            )
        }

        composable(AppRoutes.DSM) {
            DSMScreen(
                onBackClick = {
                    navController.popBackStack()
                },

                onGeneralSellerClick = {
                    if (navController.currentDestination?.route == AppRoutes.DSM) {
                        navController.navigate(AppRoutes.GENERAL_SELLER_DSM)
                    }
                },

                onWindClick = {
                    // Future
                },

                onSolarClick = {
                    // Future
                }

            )
        }

        composable(AppRoutes.GENERAL_SELLER_DSM) {

            GeneralSellerDsmScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}