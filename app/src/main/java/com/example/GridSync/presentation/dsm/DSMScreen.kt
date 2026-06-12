package com.example.GridSync.presentation.dsm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.GridSync.R
import com.example.GridSync.presentation.dsm.components.DsmTypeCard

@Composable
fun DSMScreen(
    onBackClick: () -> Unit = {},
    onGeneralSellerClick: () -> Unit = {},
    onWindClick: () -> Unit = {},
    onSolarClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_dsm_top))
        )

        DSMHeader(onBackClick = onBackClick)

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))
        )

        Text(
            text = stringResource(id = R.string.dsm_select_type),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))
        )

        DsmTypeCard(
            icon = Icons.Default.Bolt,
            title = stringResource(id = R.string.dsm_type_general_seller),
            description = stringResource(id = R.string.dsm_type_general_seller_desc),
            onClick = onGeneralSellerClick
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.padding_small))
        )

        DsmTypeCard(
            icon = Icons.Default.Air,
            title = stringResource(id = R.string.dsm_type_wind),
            description = stringResource(id = R.string.dsm_type_wind_desc),
            onClick = onWindClick
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.padding_small))
        )

        DsmTypeCard(
            icon = Icons.Default.WbSunny,
            title = stringResource(id = R.string.dsm_type_solar),
            description = stringResource(id = R.string.dsm_type_solar_desc),
            onClick = onSolarClick
        )
    }
}

@Composable
private fun DSMHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.common_back),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column {
            Text(
                text = stringResource(id = R.string.dsm_screen_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(id = R.string.dsm_screen_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}