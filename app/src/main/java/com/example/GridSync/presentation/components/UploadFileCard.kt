package com.example.GridSync.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.GridSync.R

@Composable
fun UploadFileCard(
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.upload_card_height))
            .clickable(enabled = enabled) {
                onClick()
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius))
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.UploadFile,
                contentDescription = stringResource(id = R.string.common_upload_icon),
                modifier = Modifier.size(dimensionResource(id = R.dimen.upload_icon_size)),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium))
            )

            Text(
                text = stringResource(id = R.string.upload_file_card_tap),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_tiny))
            )

            Text(
                text = stringResource(id = R.string.upload_file_card_format),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}