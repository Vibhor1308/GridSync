package com.example.GridSync.presentation.dsm.common.parsing

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun parseBigDecimal(
    value: String?,
    columnName: String,
    context: ParsingContext
): BigDecimal? {

    val sanitizedValue = value?.trim()

    if (sanitizedValue.isNullOrBlank()) {
        context.errorCollector.add(
            ParsingError(
                rowNumber = context.rowNumber,
                columnName = columnName,
                value = sanitizedValue,
                type = FileParsingErrorType.MISSING_VALUE
            )
        )
        return null
    }

    return sanitizedValue.toBigDecimalOrNull()
        ?: run {
            context.errorCollector.add(
                ParsingError(
                    rowNumber = context.rowNumber,
                    columnName = columnName,
                    value = sanitizedValue,
                    type = FileParsingErrorType.INVALID_DECIMAL
                )
            )
            null
        }
}

fun parseDate(
    value: String?,
    columnName: String,
    context: ParsingContext
): LocalDate? {

    val sanitizedValue = value?.trim()

    if (sanitizedValue.isNullOrBlank()) {
        context.errorCollector.add(
            ParsingError(
                rowNumber = context.rowNumber,
                columnName = columnName,
                value = value,
                type = FileParsingErrorType.MISSING_VALUE
            )
        )
        return null
    }

    return try {
        LocalDate.parse(sanitizedValue)
    } catch (_: Exception) {
        context.errorCollector.add(
            ParsingError(
                rowNumber = context.rowNumber,
                columnName = columnName,
                value = value,
                type = FileParsingErrorType.INVALID_DATE
            )
        )
        null
    }
}

private val TIME_FORMATTER =
    DateTimeFormatter.ofPattern("HH:mm:ss")

fun parseTime(
    value: String?,
    columnName: String,
    context: ParsingContext
): LocalTime? {

    val sanitizedValue = value?.trim()

    if (sanitizedValue.isNullOrBlank()) {
        context.errorCollector.add(
            ParsingError(
                rowNumber = context.rowNumber,
                columnName = columnName,
                value = value,
                type = FileParsingErrorType.MISSING_VALUE
            )
        )
        return null
    }

    return try {
        LocalTime.parse(
            sanitizedValue,
            TIME_FORMATTER
        )
    } catch (_: Exception) {
        context.errorCollector.add(
            ParsingError(
                rowNumber = context.rowNumber,
                columnName = columnName,
                value = value,
                type = FileParsingErrorType.INVALID_TIME
            )
        )
        null
    }
}