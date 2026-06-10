package com.example.GridSync.presentation.dsm.common

import android.net.Uri

data class DsmWorkflowUiState(

    val dsmType: DsmType? = null,
    val selectedFileName: String? = null,
    val selectedFileUri: Uri? = null,
    val isFileSelected: Boolean = false

)
