package com.example.GridSync.presentation.dsm.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.GridSync.presentation.dsm.common.FileMetadata
import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * Utility to parse metadata from CSV and Excel files.
 */

private val DATE_FORMATTERS = listOf(
    DateTimeFormatter.ISO_LOCAL_DATE,
    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
    DateTimeFormatter.ofPattern("dd-MMM-yyyy")
)

private val TIME_FORMATTERS = listOf(
    DateTimeFormatter.ISO_LOCAL_TIME,
    DateTimeFormatter.ofPattern("HH:mm"),
    DateTimeFormatter.ofPattern("H:mm"),
    DateTimeFormatter.ofPattern("HH:mm:ss"),
    DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("H.mm") // Some industrial formats use dots
)

private const val TAG = "DSMFileParser"

fun readCsvMetadata(
    context: Context,
    uri: Uri
): FileMetadata {

    Log.d(TAG,"[readCsvMetadata] start.")

    context.contentResolver
        .openInputStream(uri)
        ?.bufferedReader()
        ?.use { reader ->

            val headerLine = reader.readLine()

            val headers =
                headerLine
                    ?.split(",")
                    ?.map { it.trim() }
                    ?: emptyList()

            val dateColumnIndex =
                headers.indexOfFirst {
                    it.equals(
                        "date",
                        ignoreCase = true
                    )
                }

            val timeColumnIndex =
                headers.indexOfFirst {
                    it.equals(
                        "time",
                        ignoreCase = true
                    )
                }

            var detectedStartDate: LocalDate? = null

            var detectedEndDate: LocalDate? = null

            val timeBlocks =
                mutableListOf<DsmTimeBlock>()

            var rowCount = 0

            var line: String?

            while (
                reader.readLine().also {
                    line = it
                } != null
            ) {

                rowCount++

                val values =
                    line!!
                        .split(",")
                        .map {
                            it.trim()
                        }

                // Date Extraction
                if (
                    dateColumnIndex >= 0 &&
                    values.size > dateColumnIndex
                ) {

                    val parsedDate =
                        parseLocalDate(
                            values[dateColumnIndex]
                        )

                    if (parsedDate != null) {

                        if (
                            detectedStartDate == null ||
                            parsedDate.isBefore(
                                detectedStartDate
                            )
                        ) {
                            detectedStartDate =
                                parsedDate
                        }

                        if (
                            detectedEndDate == null ||
                            parsedDate.isAfter(
                                detectedEndDate
                            )
                        ) {
                            detectedEndDate =
                                parsedDate
                        }

                        // Create DsmTimeBlock
                        if (
                            timeColumnIndex >= 0 &&
                            values.size > timeColumnIndex
                        ) {

                            val timeString = values[timeColumnIndex]
                            val parsedTime = parseLocalTime(timeString)

                            if (parsedTime != null) {
                                timeBlocks.add(
                                    DsmTimeBlock(
                                        date = parsedDate,
                                        time = parsedTime
                                    )
                                )
                            } else {
                                Log.w(TAG, "[readCsvMetadata] Failed to parse time: $timeString at row $rowCount")
                            }
                        }
                    }
                }
            }

            Log.d(TAG, "[readCsvMetadata], rowCount : $rowCount, timeBlocks : ${timeBlocks.size}")

            return FileMetadata(

                rowCount = rowCount,

                columnCount = headers.size,

                fileType = "CSV File",

                sheetName = null,

                headers = headers,

                detectedStartDate =
                    detectedStartDate,

                detectedEndDate =
                    detectedEndDate,

                timeBlocks = timeBlocks
            )
        }

    throw IllegalArgumentException(
        "Unable to open CSV file"
    )
}
fun readExcelMetadata(
    context: Context,
    uri: Uri
): FileMetadata {
    Log.d(TAG,"[readExcelMetadata] start.")
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        WorkbookFactory.create(inputStream).use { workbook ->
            val sheet = workbook.getSheetAt(0)
            val headerRow = sheet.getRow(0)
            
            val headers = mutableListOf<String>()
            var dateColumnIndex = -1
            
            if (headerRow != null) {
                for (i in 0 until headerRow.lastCellNum) {
                    val cell = headerRow.getCell(i)
                    val headerName = cell?.toString()?.trim() ?: ""
                    headers.add(headerName)
                    if (headerName.equals("date", ignoreCase = true)) {
                        dateColumnIndex = i
                    }
                }
            }

            var detectedStartDate: LocalDate? = null
            var detectedEndDate: LocalDate? = null

            if (dateColumnIndex >= 0) {
                for (rowIndex in 1..sheet.lastRowNum) {
                    val row = sheet.getRow(rowIndex) ?: continue
                    val cell = row.getCell(dateColumnIndex) ?: continue
                    
                    val parsedDate = try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cell.dateCellValue.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        } else {
                            parseLocalDate(cell.toString().trim())
                        }
                    } catch (_: Exception) {
                        null
                    }

                    if (parsedDate != null) {
                        if (detectedStartDate == null || parsedDate.isBefore(detectedStartDate)) {
                            detectedStartDate = parsedDate
                        }
                        if (detectedEndDate == null || parsedDate.isAfter(detectedEndDate)) {
                            detectedEndDate = parsedDate
                        }
                    }
                }
            }

            return FileMetadata(
                rowCount = sheet.physicalNumberOfRows,
                columnCount = headers.size,
                fileType = if (workbook::class.java.simpleName.contains("XSSF")) "Excel Workbook (.xlsx)" else "Excel 97-2003 (.xls)",
                sheetName = sheet.sheetName,
                headers = headers,
                detectedStartDate = detectedStartDate,
                detectedEndDate = detectedEndDate
            )
        }
    }

    throw IllegalArgumentException("Unable to open Excel file")
}

private fun parseLocalDate(dateString: String): LocalDate? {
    if (dateString.isEmpty()) return null
    for (formatter in DATE_FORMATTERS) {
        try {
            return LocalDate.parse(dateString, formatter)
        } catch (e: DateTimeParseException) {
            continue
        }
    }
    return null
}

private fun parseLocalTime(timeString: String): LocalTime? {
    if (timeString.isEmpty()) return null
    for (formatter in TIME_FORMATTERS) {
        try {
            return LocalTime.parse(timeString, formatter)
        } catch (e: DateTimeParseException) {
            continue
        }
    }
    return null
}
