package com.example.GridSync.presentation.common

import com.example.GridSync.presentation.dsm.common.DsmWorkflowViewModel
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelectionScreen(
    dsmWorkflowViewModel: DsmWorkflowViewModel,
    onContinueClick: () -> Unit
) {

    val uiState by dsmWorkflowViewModel.uiState.collectAsState()

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
            .padding(16.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Select DSM Period",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        DateCard(
            title = "Start Date",
            date = uiState.selectedStartDate,
            formatter = formatter,
            onDateSelected = { selectedDate ->

                dsmWorkflowViewModel.updateStartDate(
                    selectedDate
                )

                // Auto-fill 7 day period
                dsmWorkflowViewModel.updateEndDate(
                    selectedDate.plusDays(6)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DateCard(
            title = "End Date",
            date = uiState.selectedEndDate,
            formatter = formatter,
            onDateSelected = {
                dsmWorkflowViewModel.updateEndDate(it)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinueClick,
            enabled = canContinue,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Continue")
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    date?.format(formatter)
                        ?: "Select Date"
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {

                    val initialDate =
                        date ?: LocalDate.now()

                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->

                            onDateSelected(
                                LocalDate.of(
                                    year,
                                    month + 1,
                                    dayOfMonth
                                )
                            )
                        },
                        initialDate.year,
                        initialDate.monthValue - 1,
                        initialDate.dayOfMonth
                    ).show()
                }
            ) {

                Text("Choose Date")
            }
        }
    }
}