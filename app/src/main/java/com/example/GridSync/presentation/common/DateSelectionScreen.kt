package com.example.GridSync.presentation.common

import android.app.DatePickerDialog
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.GridSync.R
import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.components.GeneralSellerDsmHeader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelectionScreen(
    dsmWorkflowViewModel: DsmWorkflowViewModel,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    val uiState by dsmWorkflowViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")

    val canContinue =
        uiState.selectedStartDate != null &&
                uiState.selectedEndDate != null &&
                !uiState.selectedEndDate!!.isBefore(
                    uiState.selectedStartDate
                )

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

        val headerTitle = uiState.selectedProject?.displayName?.let {
            stringResource(id = R.string.general_seller_project_dsm_title, it)
        } ?: stringResource(id = R.string.general_seller_dsm_title)

        GeneralSellerDsmHeader(
            onBackClick = onBackClick,
            title = headerTitle,
            subtitle = stringResource(id = R.string.date_selection_title)
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge))
        )

        Text(
            text = stringResource(id = R.string.date_selection_subtitle),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_large))
        )

        DateCard(
            title = stringResource(id = R.string.date_selection_start_date),
            date = uiState.selectedStartDate,
            formatter = formatter,
            onDateSelected = { selectedDate ->
                dsmWorkflowViewModel.updateStartDate(selectedDate)
                // Auto-fill 7 day period
                dsmWorkflowViewModel.updateEndDate(selectedDate.plusDays(6))
            }
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium))
        )

        DateCard(
            title = stringResource(id = R.string.date_selection_end_date),
            date = uiState.selectedEndDate,
            formatter = formatter,
            onDateSelected = {
                dsmWorkflowViewModel.updateEndDate(it)
            }
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onContinueClick,
            enabled = canContinue,
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
private fun DateCard(
    title: String,
    date: LocalDate?,
    formatter: DateTimeFormatter,
    onDateSelected: (LocalDate) -> Unit
) {

    val context = LocalContext.current

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

        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_tiny))
            )

            Text(
                text = date?.format(formatter) ?: stringResource(id = R.string.date_selection_select_hint),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small))
            )

            TextButton(
                onClick = {
                    val initialDate = date ?: LocalDate.now()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
                        },
                        initialDate.year,
                        initialDate.monthValue - 1,
                        initialDate.dayOfMonth
                    ).show()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.date_selection_choose_button),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
