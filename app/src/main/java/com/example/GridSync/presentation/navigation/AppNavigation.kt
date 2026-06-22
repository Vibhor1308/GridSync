package com.example.GridSync.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.GridSync.presentation.common.DateSelectionScreen
import com.example.GridSync.presentation.dashboard.DashboardScreen
import com.example.GridSync.presentation.dsm.DSMScreen
import com.example.GridSync.presentation.dsm.common.DsmType
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.FileSelectionScreen
import com.example.GridSync.presentation.dsm.generalsellerdsm.GeneralSellerViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.ProjectSelectionScreen
import com.example.GridSync.presentation.dsm.validation.FileValidationScreen
import com.example.GridSync.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val dsmWorkflowViewModel: DsmWorkflowViewModel = viewModel()
    val generalSellerViewModel:
            GeneralSellerViewModel =
        viewModel()

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
                        dsmWorkflowViewModel.clearAll()
                        dsmWorkflowViewModel.setDsmType(DsmType.GENERAL_SELLER)
                        generalSellerViewModel.clearSelection()
                        navController.navigate(AppRoutes.GENERAL_SELLER_PROJECT_SELECTION)
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

        composable(
            AppRoutes.GENERAL_SELLER_PROJECT_SELECTION
        ) {

            ProjectSelectionScreen(

                viewModel = generalSellerViewModel,
                onBackClick = {
                    navController.popBackStack()
                },

                onContinueClick = {
                    dsmWorkflowViewModel.setProject(generalSellerViewModel.uiState.value.selectedProject)
                    dsmWorkflowViewModel.clearDateSelection()
                    navController.navigate(
                        AppRoutes.DATE_SELECTION
                    )
                }
            )
        }

        composable(AppRoutes.FILE_SELECTION) {

            FileSelectionScreen(
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

            val context = androidx.compose.ui.platform.LocalContext.current

            FileValidationScreen(
                dsmWorkflowViewModel = dsmWorkflowViewModel,
                generalSellerViewModel = generalSellerViewModel,
                onBackClick = {
                    navController.popBackStack()
                },

                onContinueClick = {

                    val selectedFileUri =
                        dsmWorkflowViewModel
                            .uiState
                            .value
                            .selectedFileUri

                    if (selectedFileUri != null) {

                        generalSellerViewModel
                            .generateAp01Output(
                                context = context,
                                inputFileUri = selectedFileUri
                            )
                    }
                }
            )
        }

        composable(
            route = AppRoutes.DATE_SELECTION
        ) {

            DateSelectionScreen(

                dsmWorkflowViewModel =
                    dsmWorkflowViewModel,

                onBackClick = {
                    navController.popBackStack()
                },

                onContinueClick = {
                    dsmWorkflowViewModel.clearFileSelection()
                    navController.navigate(
                        AppRoutes.FILE_SELECTION
                    )
                }
            )
        }
    }
}