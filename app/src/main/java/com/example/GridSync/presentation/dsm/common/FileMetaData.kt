package com.example.GridSync.presentation.dsm.common

data class FileMetadata(
    val rowCount: Int,
    val columnCount: Int,
    val fileType: String,
    val sheetName: String? = null,
    val headers: List<String> = emptyList()
)