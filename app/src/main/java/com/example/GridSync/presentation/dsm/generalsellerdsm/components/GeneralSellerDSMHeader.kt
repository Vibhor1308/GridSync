package com.example.GridSync.presentation.dsm.generalsellerdsm.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.res.stringResource
import com.example.GridSync.R

@Composable
fun GeneralSellerDsmHeader(
    onBackClick: () -> Unit,
    title: String = stringResource(id = R.string.general_seller_dsm_title),
    subtitle: String = stringResource(id = R.string.general_seller_dsm_subtitle)
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBackClick
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.common_back),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}