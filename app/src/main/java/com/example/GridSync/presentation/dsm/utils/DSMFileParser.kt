package com.example.GridSync.presentation.dsm.utils

import android.content.Context
import android.net.Uri
import com.example.GridSync.presentation.dsm.common.FileMetadata
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Utility to parse metadata from CSV and Excel files.
 */

fun readCsvMetadata(
    context: Context,
    uri: Uri
): FileMetadata {

    context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { reader ->
        val headerLine = reader.readLine()
        val headers = headerLine?.split(",")?.map { it.trim() } ?: emptyList()

        var detectedStartDate: LocalDate? = null
        var detectedEndDate: LocalDate? = null

        val dateColumnIndex =
            headers.indexOfFirst {
                it.equals(
                    "date",
                    ignoreCase = true
                )
            }
        
        // Count remaining lines for row count
        var rowCount = if (headerLine != null) 1 else 0
        var line: String?

        while (
            reader.readLine().also {
                line = it
            } != null
        ) {

            rowCount++

            val values =
                line!!.split(",")

            if (
                dateColumnIndex >= 0 &&
                values.size > dateColumnIndex
            ) {

                val currentDate =
                    LocalDate.parse(
                        values[dateColumnIndex].trim(),
                        DateTimeFormatter.ISO_LOCAL_DATE
                    )

                if (detectedStartDate == null) {

                    detectedStartDate =
                        currentDate
                }

                detectedEndDate =
                    currentDate
            }
        }

        return FileMetadata(
            rowCount = rowCount,
            columnCount = headers.size,
            fileType = "CSV File",
            sheetName = null,
            headers = headers,
            detectedStartDate =
                detectedStartDate,
            detectedEndDate =
                detectedEndDate
        )
    }

    throw IllegalArgumentException("Unable to open CSV file")
}

fun readExcelMetadata(
    context: Context,
    uri: Uri
): FileMetadata {

    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        // WorkbookFactory automatically handles both .xls and .xlsx
        WorkbookFactory.create(inputStream).use { workbook ->
            val sheet = workbook.getSheetAt(0)
            val headerRow = sheet.getRow(0)
            
            val headers = mutableListOf<String>()
            if (headerRow != null) {
                for (cell in headerRow) {
                    headers.add(cell.toString().trim())
                }
            }

            return FileMetadata(
                rowCount = sheet.physicalNumberOfRows,
                columnCount = headerRow?.physicalNumberOfCells ?: 0,
                fileType = if (workbook::class.java.simpleName.contains("XSSF")) "Excel Workbook (.xlsx)" else "Excel 97-2003 (.xls)",
                sheetName = sheet.sheetName,
                headers = headers
            )
        }
    }

    throw IllegalArgumentException("Unable to open Excel file")
}
