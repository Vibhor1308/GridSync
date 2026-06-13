package com.example.GridSync.presentation.dsm.common

import android.net.Uri

import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult

data class DsmWorkflowUiState(

    val dsmType: DsmType? = null,
    val selectedProject: GeneralSellerProject? = null,
    val selectedFileName: String? = null,
    val selectedFileUri: Uri? = null,
    val isFileSelected: Boolean = false,
    val isProcessingFile: Boolean = false,
    val fileMetadata: FileMetadata? = null,
    val validationResults: List<ValidationResult> = emptyList()

)
