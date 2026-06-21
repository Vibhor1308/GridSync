package dsm.ap01.calculation

import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01CsvFileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01ParseResult
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PspInjectionCalculationIntegrationTest {

    @Test
    fun shouldMapAllRecordsFromRealAp01File() = runTest {

        // Arrange

        val inputUri = "/home/vibhor/Downloads/AP-01-Injection/commercial_dev2022_greenko_kurnool_psp_firm.csv"
        val csvFileReader = Ap01CsvFileReader()

        val mapper = PspCalculationMapper()

        // Act

        val parseResult = csvFileReader.read(
            file = File(inputUri)
        )

        assertIs<Ap01ParseResult.Success>(parseResult)
        val inputRecords = parseResult.records

        val calculationRecords = inputRecords.map(
            mapper::map
        )

        // Assert

        assertEquals(
            672,
            inputRecords.size
        )

        assertEquals(
            672,
            calculationRecords.size
        )

        calculationRecords
            .take(10)
            .forEachIndexed { index, record ->

                println(
                    """
                ROW ${index + 1}
                DateTime : ${record.input.timeBlock}
                Deviation : ${record.deviation}
                Deviation % : ${record.deviationPercentage}
                Dev L1 : ${record.deviationLevel1}
                Dev L2 : ${record.deviationLevel2}
                OI L1 : ${record.overInjectionChargeLevel1}
                OI L2 : ${record.overInjectionChargeLevel2}
                UI L1 : ${record.underInjectionChargeLevel1}
                UI L2 : ${record.underInjectionChargeLevel2}
                Drawal : ${record.drawalCharge}
                Total OI : ${record.totalOverInjectionCharge}
                Total UI : ${record.totalUnderInjectionCharge}
                """.trimIndent()
                )
            }
    }
}
