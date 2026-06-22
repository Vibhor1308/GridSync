package com.example.GridSync.presentation.dsm.common.parsing

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private val DATE_FORMATTERS = listOf(
    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
    DateTimeFormatter.ofPattern("d-M-yyyy"),
    DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
    DateTimeFormatter.ISO_LOCAL_DATE
)

private val TIME_FORMATTERS = listOf(
    DateTimeFormatter.ofPattern("H:mm:ss"),
    DateTimeFormatter.ofPattern("HH:mm:ss"),
    DateTimeFormatter.ofPattern("H:mm"),
    DateTimeFormatter.ofPattern("HH:mm"),
    DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("H.mm"),
    DateTimeFormatter.ISO_LOCAL_TIME
)

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

    val sanitizedValue = value?.trim() ?: ""

    if (sanitizedValue.isBlank()) {
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

    for (formatter in DATE_FORMATTERS) {
        try {
            return LocalDate.parse(sanitizedValue, formatter)
        } catch (_: DateTimeParseException) {
            continue
        }
    }

    context.errorCollector.add(
        ParsingError(
            rowNumber = context.rowNumber,
            columnName = columnName,
            value = value,
            type = FileParsingErrorType.INVALID_DATE
        )
    )
    return null
}

fun parseTime(
    value: String?,
    columnName: String,
    context: ParsingContext
): LocalTime? {

    val sanitizedValue = value?.trim() ?: ""

    if (sanitizedValue.isBlank()) {
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

    for (formatter in TIME_FORMATTERS) {
        try {
            return LocalTime.parse(sanitizedValue, formatter)
        } catch (_: DateTimeParseException) {
            continue
        }
    }

    context.errorCollector.add(
        ParsingError(
            rowNumber = context.rowNumber,
            columnName = columnName,
            value = value,
            type = FileParsingErrorType.INVALID_TIME
        )
    )
    return null
}
