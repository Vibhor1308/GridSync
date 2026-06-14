package com.example.GridSync.presentation.dsm.common

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class DsmWorkflowViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            DsmWorkflowUiState()
        )

    val uiState = _uiState.asStateFlow()

    fun setDsmType(
        dsmType: DsmType
    ) {

        _uiState.value =
            _uiState.value.copy(
                dsmType = dsmType
            )
    }

    fun setProject(
        project: GeneralSellerProject?
    ) {
        _uiState.value =
            _uiState.value.copy(
                selectedProject = project
            )
    }

    fun onFileSelected(
        fileName: String?,
        uri: Uri
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedFileName = fileName,
                isFileSelected = true,
                selectedFileUri = uri,
                fileMetadata = null,
                validationResults = emptyList()
            )
    }

    fun setFileMetadata(
        metadata: FileMetadata
    ) {
        _uiState.value = _uiState.value.copy(
            fileMetadata = metadata,
            isProcessingFile = false
        )
    }

    fun updateStartDate(
        date: LocalDate
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedStartDate = date
            )
    }

    fun updateEndDate(
        date: LocalDate
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedEndDate = date
            )
    }

    fun setProcessing(processing: Boolean) {
        _uiState.value = _uiState.value.copy(isProcessingFile = processing)
    }

    fun clearAll() {
        _uiState.value = DsmWorkflowUiState()
    }

    fun clearDateSelection() {
        _uiState.value = _uiState.value.copy(
            selectedStartDate = null,
            selectedEndDate = null
        )
    }

    fun clearFileSelection() {
        _uiState.value = _uiState.value.copy(
            selectedFileName = null,
            selectedFileUri = null,
            isFileSelected = false,
            fileMetadata = null,
            validationResults = emptyList()
        )
    }

    fun setValidationResults(
        results: List<ValidationResult>
    ) {

        _uiState.value =
            _uiState.value.copy(
                validationResults = results
            )
    }
}
