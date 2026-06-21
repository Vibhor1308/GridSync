package dsm.ap01

import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.output.Ap01WorkbookGenerator
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.output.GenerationSheetColumns
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

class Ap01WorkbookGeneratorTest {

    @Test
    fun shouldPopulateGenerationSheet() {

        // Arrange

        val workbook = WorkbookFactory.create(
            File(
                "src/test/resources/ap_01_injection_output_format.xlsx"
            )
        )

        val generator = Ap01WorkbookGenerator()

        val mapper = PspCalculationMapper()

        val inputRecords = listOf(
            Ap01InputRecord(
                timeBlock = DsmTimeBlock(
                    date = LocalDate.of(2026, 5, 25),
                    time = LocalTime.of(0, 15)
                ),
                scheduledGeneration = BigDecimal("100"),
                actualGeneration = BigDecimal("110"),
                frequency = BigDecimal("50.00"),
                rate = BigDecimal.ZERO,
                ppaRate = BigDecimal("4")
            )
        )

        val calculationRecords =
            inputRecords.map { inputRecord ->
                mapper.map(inputRecord)
            }

        // Act

        generator.generate(
            workbook = workbook,
            inputRecords = inputRecords,
            calculationRecords = calculationRecords
        )

        // Assert

        val sheet =
            workbook.getSheet("Generation")

        val row =
            sheet.getRow(2)

        assertEquals(
            "2026-05-25",
            row.getCell(
                GenerationSheetColumns.DATE
            ).stringCellValue
        )

        assertEquals(
            "00:15",
            row.getCell(
                GenerationSheetColumns.TIME_BLOCK
            ).stringCellValue
        )

        assertEquals(
            100.0,
            row.getCell(
                GenerationSheetColumns.SCHEDULE
            ).numericCellValue,
            0.001
        )

        assertEquals(
            110.0,
            row.getCell(
                GenerationSheetColumns.ACTUAL
            ).numericCellValue,
            0.001
        )

        assertEquals(
            10.0,
            row.getCell(
                GenerationSheetColumns.DEVIATION
            ).numericCellValue,
            0.001
        )

        assertEquals(
            calculationRecords.first()
                .deviationPercentage
                .toDouble(),
            row.getCell(
                GenerationSheetColumns.DEVIATION_PERCENTAGE
            ).numericCellValue,
            0.001
        )

        assertEquals(
            4.0,
            row.getCell(
                GenerationSheetColumns.PPA_RATE
            ).numericCellValue,
            0.001
        )

        assertEquals(
            400.0,
            row.getCell(
                GenerationSheetColumns.UPTO_10_PERCENT
            ).numericCellValue,
            0.001
        )

        assertEquals(
            0.0,
            row.getCell(
                GenerationSheetColumns.BEYOND_10_PERCENT
            ).numericCellValue,
            0.001
        )

        assertEquals(
            400.0,
            row.getCell(
                GenerationSheetColumns.TOTAL_DEVIATION_CHARGE
            ).numericCellValue,
            0.001
        )
    }
}