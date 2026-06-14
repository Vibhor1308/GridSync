package com.example.GridSync.presentation.dsm.common

import java.time.LocalDate

data class FileMetadata(
    val rowCount: Int,
    val columnCount: Int,
    val fileType: String,
    val sheetName: String? = null,
    val headers: List<String> = emptyList(),
    val detectedStartDate: LocalDate? = null,
    val detectedEndDate: LocalDate? = null
)
