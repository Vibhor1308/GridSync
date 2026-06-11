package com.example.GridSync.presentation.dsm.common

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun onFileSelected(
        fileName: String?,
        uri: Uri
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedFileName = fileName,
                isFileSelected = true,
                selectedFileUri = uri
            )
    }

    fun setFileMetadata(
        metadata: FileMetadata
    ){
        _uiState.value = _uiState.value.copy(
            fileMetadata = metadata,
            isProcessingFile = false
        )
    }

    fun setProcessing(processing: Boolean) {
        _uiState.value = _uiState.value.copy(isProcessingFile = processing)
    }

    fun clearFileSelection() {
        _uiState.value = _uiState.value.copy(
            selectedFileName = null,
            selectedFileUri = null,
            isFileSelected = false,
            fileMetadata = null
        )
    }
}