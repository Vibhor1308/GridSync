package dsm.ap01.calculation

import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class PspCalculationMapperTest {

    @Test
    fun shouldCalculateOverInjectionChargeLevel1ForBandFrequency() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("110"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal("0"),
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertEquals(
            0,
            BigDecimal("400")
                .compareTo(result.overInjectionChargeLevel1)
        )
    }

    @Test
    fun shouldReturnZeroForUnderInjectionAtBandFrequency() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal("0"),
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertEquals(
            BigDecimal.ZERO,
            result.overInjectionChargeLevel1
        )
    }

    @Test
    fun shouldReturnZeroForOverFrequency1() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("110"),
            frequency = BigDecimal("50.06"),
            rate = BigDecimal("0"),
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertEquals(
            BigDecimal.ZERO,
            result.overInjectionChargeLevel1
        )
    }

}