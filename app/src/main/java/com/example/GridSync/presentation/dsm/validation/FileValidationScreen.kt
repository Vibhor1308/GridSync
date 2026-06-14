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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.GridSync.R
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus
import com.example.GridSync.presentation.dsm.generalsellerdsm.GeneralSellerViewModel
import com.example.GridSync.presentation.dsm.utils.readCsvMetadata
import com.example.GridSync.presentation.dsm.utils.readExcelMetadata
import com.example.GridSync.ui.theme.ErrorRed
import com.example.GridSync.ui.theme.SuccessGreen
import com.example.GridSync.ui.theme.WarningYellow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FileValidationScreen(
    dsmWorkflowViewModel: DsmWorkflowViewModel,
    generalSellerViewModel: GeneralSellerViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    val dsmWorkflowUiState by dsmWorkflowViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(dsmWorkflowUiState.fileMetadata) {

        val metadata =
            dsmWorkflowUiState.fileMetadata
                ?: return@LaunchedEffect

        if (
            dsmWorkflowUiState.validationResults.isNotEmpty()
        ) {
            return@LaunchedEffect
        }

        val fileName =
            dsmWorkflowUiState.selectedFileName
                ?: return@LaunchedEffect

        val results =
            generalSellerViewModel.runValidation(
                fileName = fileName,
                metadata = metadata,
                dsmWorkflowUiState.selectedStartDate,
                dsmWorkflowUiState.selectedEndDate
            )

        dsmWorkflowViewModel
            .setValidationResults(results)
    }

    LaunchedEffect(dsmWorkflowUiState.selectedFileUri) {

        if (dsmWorkflowUiState.fileMetadata != null) {
            return@LaunchedEffect
        }

        val uri = dsmWorkflowUiState.selectedFileUri ?: return@LaunchedEffect
        val fileName = dsmWorkflowUiState.selectedFileName ?: return@LaunchedEffect
        
        dsmWorkflowViewModel.setProcessing(true)
        
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
                dsmWorkflowViewModel.setFileMetadata(it)
            } ?: dsmWorkflowViewModel.setProcessing(false)

        } catch (e: Exception) {
            dsmWorkflowViewModel.setProcessing(false)
            Log.e("FileValidation", "Failed to read file", e)
        }
    }

    val canProceed =
        dsmWorkflowUiState.validationResults.isNotEmpty() &&
                dsmWorkflowUiState.validationResults.none {
                    it.status == ValidationStatus.FAIL
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

        val headerTitle = dsmWorkflowUiState.selectedProject?.displayName?.let {
            stringResource(id = R.string.general_seller_project_dsm_title, it)
        } ?: stringResource(id = R.string.file_validation_title)

        FileValidationHeader(
            onBackClick = onBackClick,
            title = headerTitle
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        if (dsmWorkflowUiState.isProcessingFile) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = dsmWorkflowUiState.selectedFileName ?: stringResource(id = R.string.general_seller_dsm_no_file),
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

                    val metadata = dsmWorkflowUiState.fileMetadata

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
                    modifier = Modifier.height(dimensionResource(R.dimen.spacing_large))
                )

                dsmWorkflowUiState.validationResults.forEach { result ->

                    val statusColor = getStatusColor(result.status)

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = getStatusSymbol(result.status),
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

                        Text(
                            text = result.validationName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = result.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_large))
                    )

                    Spacer(
                        modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small))
                    )
                }

                if (dsmWorkflowUiState.validationResults.isNotEmpty()) {
                    Spacer(
                        modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium))
                    )

                    Text(
                        text = if (canProceed)
                            stringResource(id = R.string.file_validation_status_ready)
                        else
                            stringResource(id = R.string.file_validation_status_failed),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (canProceed) SuccessGreen else ErrorRed,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(
                        modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
        }

        Button(
            onClick = onContinueClick,
            enabled = !dsmWorkflowUiState.isProcessingFile && canProceed,
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
private fun getStatusColor(
    status: ValidationStatus
): Color {
    return when (status) {
        ValidationStatus.PASS -> SuccessGreen
        ValidationStatus.FAIL -> ErrorRed
        ValidationStatus.WARNING -> WarningYellow
    }
}

private fun getStatusSymbol(
    status: ValidationStatus
): String {

    return when(status) {

        ValidationStatus.PASS -> "✓"

        ValidationStatus.FAIL -> "✗"

        ValidationStatus.WARNING -> "⚠"
    }
}

@Composable
private fun FileValidationHeader(
    onBackClick: () -> Unit,
    title: String = stringResource(id = R.string.file_validation_title)
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
                text = title,
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
