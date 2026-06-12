package com.example.GridSync.presentation.dsm.generalsellerdsm

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import com.example.GridSync.R
import com.example.GridSync.presentation.components.UploadFileCard
import com.example.GridSync.presentation.dsm.common.DsmType
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.components.GeneralSellerDsmHeader
import com.example.GridSync.utils.getFileName

@Composable
fun GeneralSellerDsmScreen(
    viewModel: DsmWorkflowViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val isInteractionEnabled = lifecycleState.isAtLeast(Lifecycle.State.RESUMED)

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            uri?.let {

                val fileName =
                    getFileName(
                        context,
                        it
                    )

                viewModel.onFileSelected(
                    fileName,
                    uri
                )
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .statusBarsPadding()
            .navigationBarsPadding()
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

        uiState.selectedProject?.let { project ->
            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_small))
            )
            Text(
                text = project.displayName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        UploadFileCard(
            enabled = isInteractionEnabled,
            onClick = {
                launcher.launch(
                    arrayOf(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "application/vnd.ms-excel",
                        "text/csv",
                        "text/comma-separated-values",
                        "application/csv"
                    )
                )
            }

        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        Text(
            text = uiState.selectedFileName
                ?: stringResource(id = R.string.general_seller_dsm_no_file),
            style = MaterialTheme.typography.bodyLarge,
            color = if (uiState.isFileSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontWeight = if (uiState.isFileSelected) FontWeight.SemiBold else FontWeight.Normal
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onContinueClick,
            enabled = uiState.isFileSelected && isInteractionEnabled,
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
