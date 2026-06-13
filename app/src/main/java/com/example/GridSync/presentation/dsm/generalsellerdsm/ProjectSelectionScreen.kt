package com.example.GridSync.presentation.dsm.generalsellerdsm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.GridSync.R
import com.example.GridSync.presentation.dsm.generalsellerdsm.components.GeneralSellerDsmHeader
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject

@Composable
fun ProjectSelectionScreen(
    viewModel: GeneralSellerViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

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

        GeneralSellerDsmHeader(
            onBackClick = onBackClick,
            subtitle = stringResource(id = R.string.project_selection_title)
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        Text(
            text = stringResource(id = R.string.project_selection_subtitle),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_large))
        )

        GeneralSellerProject.entries.forEach { project ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.spacing_tiny))
                    .clickable {
                        viewModel.selectProject(project)
                    },
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = uiState.selectedProject == project,
                        onClick = {
                            viewModel.selectProject(project)
                        }
                    )

                    Spacer(
                        modifier = Modifier.width(dimensionResource(R.dimen.spacing_small))
                    )

                    Text(
                        text = project.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onContinueClick,
            enabled = uiState.selectedProject != null,
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
