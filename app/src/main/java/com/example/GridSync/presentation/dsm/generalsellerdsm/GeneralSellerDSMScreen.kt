package com.example.GridSync.presentation.dsm.generalsellerdsm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.GridSync.R
import com.example.GridSync.presentation.components.UploadFileCard
import com.example.GridSync.presentation.dsm.generalsellerdsm.components.GeneralSellerDsmHeader
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

@Composable
fun GeneralSellerDsmScreen(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_dsm_top))
        )

        GeneralSellerDsmHeader(
            onBackClick = onBackClick
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        Text(
            text = stringResource(id = R.string.general_seller_dsm_step_1),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_small))
        )

        Text(
            text = stringResource(id = R.string.general_seller_dsm_upload_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        UploadFileCard(
            onClick = {}
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        Text(
            text = stringResource(id = R.string.general_seller_dsm_no_file),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        ) {

            Text(
                text = stringResource(id = R.string.general_seller_dsm_continue)
            )
        }
    }
}