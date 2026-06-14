package com.example.GridSync.presentation.dsm.generalsellerdsm

import androidx.lifecycle.ViewModel
import com.example.GridSync.presentation.dsm.common.FileMetadata
import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.generalsellerdsm.configuration.GeneralSellerConfigurationProvider
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerUiState
import com.example.GridSync.presentation.dsm.generalsellerdsm.validation.GeneralSellerValidatorFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class GeneralSellerViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            GeneralSellerUiState()
        )

    val uiState = _uiState.asStateFlow()

    fun selectProject(
        project: GeneralSellerProject
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedProject = project
            )
    }

    fun runValidation(
        fileName: String,
        metadata: FileMetadata,
        selectedStartDate: LocalDate?,
        selectedEndDate: LocalDate?
    ): List<ValidationResult> {

        val project =
            uiState.value.selectedProject
                ?: return emptyList()

        val configuration =
            GeneralSellerConfigurationProvider
                .getConfiguration(project)

        val validationContext = ValidationContext(
            fileName = fileName,
            recordCount = metadata.rowCount,
            headers = metadata.headers,
            selectedStartDate =
                selectedStartDate,
            selectedEndDate =
                selectedEndDate,
            detectedStartDate =
                metadata.detectedStartDate,
            detectedEndDate =
                metadata.detectedEndDate,
            configuration = configuration,
            timeBlocks = metadata.timeBlocks
        )

        val validator =
            GeneralSellerValidatorFactory
                .getValidator(project)

        return validator.validate(
            validationContext
        )
    }

    fun clearSelection() {

        _uiState.value =
            _uiState.value.copy(
                selectedProject = null
            )
    }
}