package com.example.GridSync.presentation.dsm.common.parsing

data class ParsingError(
    val rowNumber: Int,
    val columnName: String,
    val value: String?,
    val type: FileParsingErrorType
) {
    fun toHumanReadableMessage(): String {
        val detail = if (value != null) " (Value: '$value')" else ""
        return when (type) {
            FileParsingErrorType.MISSING_VALUE -> "Row $rowNumber: Column '$columnName' is missing."
            FileParsingErrorType.INVALID_DATE -> "Row $rowNumber: Column '$columnName' has an invalid date format$detail."
            FileParsingErrorType.INVALID_TIME -> "Row $rowNumber: Column '$columnName' has an invalid time format$detail."
            FileParsingErrorType.INVALID_DECIMAL -> "Row $rowNumber: Column '$columnName' should be a number$detail."
        }
    }
}
