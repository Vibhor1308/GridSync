package com.example.GridSync.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.GridSync.R
import com.example.GridSync.presentation.components.ModuleCard
import com.example.GridSync.ui.theme.DsmBlue
import com.example.GridSync.ui.theme.FutureOrange
import com.example.GridSync.ui.theme.ReportsPurple
import com.example.GridSync.ui.theme.RtdaGreen

@Composable
fun DashboardScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(70.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.gs_logo),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = "GridSync",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground // Adapts to Light/Dark automatically
                    )

                    Text(
                        text = "Power Operations Suite",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f) // Softer contrast for subtitle
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                item {
                    ModuleCard(
                        icon = Icons.Default.ElectricalServices,
                        title = "DSM",
                        subtitle = "Deviation Settlement",
                        iconColor = DsmBlue
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Analytics,
                        title = "RTDA",
                        subtitle = "Real Time Analytics",
                        iconColor = RtdaGreen
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Assessment,
                        title = "Reports",
                        subtitle = "Operational Reports",
                        iconColor = ReportsPurple
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Extension,
                        title = "Future",
                        subtitle = "Upcoming Modules",
                        iconColor = FutureOrange
                    )
                }
            }
        }
    }
}