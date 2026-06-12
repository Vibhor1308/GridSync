package com.example.GridSync.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.GridSync.presentation.dashboard.DashboardScreen
import com.example.GridSync.presentation.dsm.DSMScreen
import com.example.GridSync.presentation.dsm.common.DsmType
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.GeneralSellerDsmScreen
import com.example.GridSync.presentation.dsm.validation.FileValidationScreen
import com.example.GridSync.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val dsmWorkflowViewModel: DsmWorkflowViewModel = viewModel()

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
                        dsmWorkflowViewModel.clearFileSelection()
                        dsmWorkflowViewModel.setDsmType(DsmType.GENERAL_SELLER)
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
                viewModel = dsmWorkflowViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onContinueClick = {
                    navController.navigate(
                        AppRoutes.FILE_VALIDATION
                    )
                }
            )
        }

        composable(AppRoutes.FILE_VALIDATION) {

            FileValidationScreen(
                viewModel = dsmWorkflowViewModel,
                onBackClick = {
                    navController.popBackStack()
                },

                onContinueClick = {
                    // Next screen later
                }
            )
        }
    }
}