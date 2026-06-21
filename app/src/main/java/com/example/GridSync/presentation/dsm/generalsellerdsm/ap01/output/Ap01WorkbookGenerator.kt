package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.output

import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationRecord
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook

class Ap01WorkbookGenerator {

    fun generate(
        workbook: Workbook,
        inputRecords: List<Ap01InputRecord>,
        calculationRecords: List<PspCalculationRecord>
    ) {

        require(
            inputRecords.size == calculationRecords.size
        )

        val sheet = workbook.getSheet("Generation")

        inputRecords.indices.forEach { index ->

            val inputRecord = inputRecords[index]

            val calculationRecord = calculationRecords[index]

            val row = sheet.getRow(index + 2) ?: sheet.createRow(index + 2)

            populateRow(
                row = row, inputRecord = inputRecord, calculationRecord = calculationRecord
            )
        }
    }

    private fun populateRow(
        row: Row, inputRecord: Ap01InputRecord, calculationRecord: PspCalculationRecord
    ) {

        row.createCell(
            GenerationSheetColumns.DATE
        ).setCellValue(
            inputRecord.timeBlock.date.toString()
        )

        row.createCell(
            GenerationSheetColumns.TIME_BLOCK
        ).setCellValue(
            inputRecord.timeBlock.time.toString()
        )

        row.createCell(
            GenerationSheetColumns.SCHEDULE
        ).setCellValue(
            inputRecord.scheduledGeneration.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.ACTUAL
        ).setCellValue(
            inputRecord.actualGeneration.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.DEVIATION
        ).setCellValue(
            calculationRecord.deviation.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.DEVIATION_PERCENTAGE
        ).setCellValue(
            calculationRecord.deviationPercentage.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.PPA_RATE
        ).setCellValue(
            inputRecord.ppaRate.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.UPTO_10_PERCENT
        ).setCellValue(
            calculationRecord.upto10PercentCharge.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.BEYOND_10_PERCENT
        ).setCellValue(
            calculationRecord.beyond10PercentCharge.toDouble()
        )

        row.createCell(
            GenerationSheetColumns.TOTAL_DEVIATION_CHARGE
        ).setCellValue(
            calculationRecord.totalDeviationCharge.toDouble()
        )
    }
}