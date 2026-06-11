package com.example.GridSync.presentation.dsm.validation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.GridSync.R
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.utils.readCsvMetadata
import com.example.GridSync.presentation.dsm.utils.readExcelMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FileValidationScreen(
    viewModel: DsmWorkflowViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.selectedFileUri) {

        val uri = uiState.selectedFileUri ?: return@LaunchedEffect
        val fileName = uiState.selectedFileName ?: return@LaunchedEffect
        
        viewModel.setProcessing(true)
        
        try {
            val metadata = withContext(Dispatchers.IO) {
                when {
                    fileName.endsWith(".csv", ignoreCase = true) -> {
                        readCsvMetadata(context, uri)
                    }
                    fileName.endsWith(".xlsx", ignoreCase = true) || fileName.endsWith(".xls", ignoreCase = true) -> {
                        readExcelMetadata(context, uri)
                    }
                    else -> null
                }
            }

            metadata?.let {
                viewModel.setFileMetadata(it)
            } ?: viewModel.setProcessing(false)

        } catch (e: Exception) {
            viewModel.setProcessing(false)
            Log.e("FileValidation", "Failed to read file", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_dsm_top))
        )

        FileValidationHeader(onBackClick = onBackClick)

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        if (uiState.isProcessingFile) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Text(
                text = uiState.selectedFileName ?: stringResource(id = R.string.general_seller_dsm_no_file),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_large))
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                )
            ) {

                val metadata = uiState.fileMetadata

                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                ) {

                    MetadataItem(
                        label = stringResource(id = R.string.file_validation_rows),
                        value = metadata?.rowCount?.toString() ?: "-"
                    )

                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))
                    )

                    MetadataItem(
                        label = stringResource(id = R.string.file_validation_columns),
                        value = metadata?.columnCount?.toString() ?: "-"
                    )

                    metadata?.sheetName?.let { sheetName ->

                        Spacer(
                            modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))
                        )

                        MetadataItem(
                            label = stringResource(id = R.string.file_validation_sheet_name),
                            value = sheetName
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = onContinueClick,
            enabled = !uiState.isProcessingFile,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_large)),
            shape = MaterialTheme.shapes.large
        ) {

            Text(
                text = stringResource(id = R.string.general_seller_dsm_continue),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun FileValidationHeader(
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
                text = stringResource(id = R.string.file_validation_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(id = R.string.file_validation_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}

@Composable
private fun MetadataItem(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_tiny))
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}
