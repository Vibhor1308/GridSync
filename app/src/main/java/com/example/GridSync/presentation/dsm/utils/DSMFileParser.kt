package com.example.GridSync.presentation.dsm.utils

import android.content.Context
import android.net.Uri
import com.example.GridSync.presentation.dsm.common.FileMetadata
import org.apache.poi.ss.usermodel.WorkbookFactory

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
        
        // Count remaining lines for row count
        var rowCount = if (headerLine != null) 1 else 0
        while (reader.readLine() != null) {
            rowCount++
        }

        return FileMetadata(
            rowCount = rowCount,
            columnCount = headers.size,
            fileType = "CSV File",
            sheetName = null,
            headers = headers
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
