package com.example.GridSync.presentation.dsm.common.validation

import com.example.GridSync.presentation.dsm.common.configuration.ProjectConfiguration

data class ValidationContext(
    val fileName: String,
    val recordCount: Int,
    val headers: List<String> = emptyList(),
    val selectedStartDate: String? = null,
    val selectedEndDate: String? = null,
    val configuration: ProjectConfiguration,
){
    val normalizedHeaders: Set<String>
        get() = headers.map {
            it.trim().lowercase()
        }.toSet()
}