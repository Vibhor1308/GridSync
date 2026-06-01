package com.example.GridSync.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Power
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.GridSync.R
import com.example.GridSync.presentation.navigation.AppRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    var currentStep by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {

        delay(300)
        currentStep = 1

        delay(300)
        currentStep = 2

        delay(300)
        currentStep = 3

        delay(300)
        currentStep = 4

        delay(500)

        navController.navigate(AppRoutes.DASHBOARD) {
            popUpTo(AppRoutes.SPLASH) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.gs_logo),
            contentDescription = "GridSync Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        SplashItem(
            icon = Icons.Default.Factory,
            text = "Generation",
            active = currentStep >= 1
        )

        Text("│")

        SplashItem(
            icon = Icons.Default.CellTower,
            text = "Transmission",
            active = currentStep >= 2
        )

        Text("│")

        SplashItem(
            icon = Icons.Default.Power,
            text = "Distribution",
            active = currentStep >= 3
        )

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = currentStep >= 4
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "GridSync",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Power Operations Suite"
                )
            }
        }
    }
}

@Composable
fun SplashItem(
    icon: ImageVector,
    text: String,
    active: Boolean
) {

    val alpha by animateFloatAsState(
        targetValue = if (active) 1f else 0.3f,
        label = ""
    )

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.alpha(alpha)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            modifier = Modifier.alpha(alpha)
        )
    }
}