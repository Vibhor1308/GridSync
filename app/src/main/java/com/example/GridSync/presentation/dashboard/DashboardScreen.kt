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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_huge)))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.gs_logo),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.logo_size_medium))
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_large)))

                Column {

                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground // Adapts to Light/Dark automatically
                    )

                    Text(
                        text = stringResource(id = R.string.power_operations_suite),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f) // Softer contrast for subtitle
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
            ) {

                item {
                    ModuleCard(
                        icon = Icons.Default.ElectricalServices,
                        title = stringResource(id = R.string.module_dsm_title),
                        subtitle = stringResource(id = R.string.module_dsm_subtitle),
                        iconColor = DsmBlue
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Analytics,
                        title = stringResource(id = R.string.module_rtda_title),
                        subtitle = stringResource(id = R.string.module_rtda_subtitle),
                        iconColor = RtdaGreen
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Assessment,
                        title = stringResource(id = R.string.module_reports_title),
                        subtitle = stringResource(id = R.string.module_reports_subtitle),
                        iconColor = ReportsPurple
                    )
                }

                item {
                    ModuleCard(
                        icon = Icons.Default.Extension,
                        title = stringResource(id = R.string.module_future_title),
                        subtitle = stringResource(id = R.string.module_future_subtitle),
                        iconColor = FutureOrange
                    )
                }
            }
        }
    }
}