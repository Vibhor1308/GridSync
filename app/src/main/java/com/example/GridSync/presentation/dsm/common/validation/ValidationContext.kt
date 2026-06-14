package com.example.GridSync.presentation.dsm.common.validation

import com.example.GridSync.presentation.dsm.common.configuration.ProjectConfiguration
import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import java.time.LocalDate

data class ValidationContext(
    val fileName: String,
    val recordCount: Int,
    val headers: List<String> = emptyList(),
    val selectedStartDate: LocalDate?,
    val selectedEndDate: LocalDate?,
    val detectedStartDate: LocalDate?,
    val detectedEndDate: LocalDate?,
    val configuration: ProjectConfiguration,
    val timeBlocks: List<DsmTimeBlock>,
){
    val normalizedHeaders: Set<String>
        get() = headers.map {
            it.trim().lowercase()
        }.toSet()
}