package com.example.GridSync.presentation.dsm.common.parsing

data class ParsingError(
    val rowNumber: Int,
    val columnName: String,
    val value: String?,
    val type: FileParsingErrorType
)
