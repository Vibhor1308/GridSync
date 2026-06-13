package com.example.GridSync.presentation.dsm.common.validation

import com.example.GridSync.presentation.dsm.common.configuration.ProjectConfiguration

data class ValidationContext(
    val fileName: String,
    val recordCount: Int,
    val headers: List<String> = emptyList(),
    val selectedStartDate: String? = null,
    val selectedEndDate: String? = null,
    val configuration: ProjectConfiguration

)