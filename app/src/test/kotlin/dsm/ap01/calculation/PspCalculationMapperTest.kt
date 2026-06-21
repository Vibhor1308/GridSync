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

    @Test
    fun shouldCalculateOverInjectionChargeLevel2ForOverFrequency2() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("150"),
            frequency = BigDecimal("50.10"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-160",
            actual = result.overInjectionChargeLevel2
        )
    }

    @Test
    fun shouldReturnZeroWhenFrequencyIsNotOverFrequency2() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("150"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "0",
            actual = result.overInjectionChargeLevel2
        )
    }

    private fun assertBigDecimalEquals(
        expected: String,
        actual: BigDecimal
    ) {
        assertEquals(
            0,
            BigDecimal(expected).compareTo(actual)
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel1ForBandFrequency() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.underInjectionChargeLevel1
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel1ForFrequency50_04() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.04"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-370",
            actual = result.underInjectionChargeLevel1
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel1ForOverFrequency1() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.06"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-340",
            actual = result.underInjectionChargeLevel1
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel2ForFrequencyGreaterThanOrEqualTo50() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("50"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-1600",
            actual = result.underInjectionChargeLevel2
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel2ForFrequencyBetween49_90And50() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("50"),
            frequency = BigDecimal("49.95"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-2400",
            actual = result.underInjectionChargeLevel2
        )
    }

    @Test
    fun shouldCalculateUnderInjectionChargeLevel2ForFrequencyBelow49_90() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("50"),
            frequency = BigDecimal("49.89"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-3200",
            actual = result.underInjectionChargeLevel2
        )
    }

    @Test
    fun shouldCalculateDrawalCharge() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal.ZERO,
            actualGeneration = BigDecimal("-10"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.drawalCharge
        )
    }

    @Test
    fun shouldReturnZeroDrawalChargeWhenScheduleIsNotZero() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "0",
            actual = result.drawalCharge
        )
    }

    @Test
    fun shouldCalculateAggregatedChargesForOverInjection() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
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

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "400",
            actual = result.upto10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "0",
            actual = result.beyond10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "400",
            actual = result.totalDeviationCharge
        )
    }

    @Test
    fun shouldCalculateAggregatedChargesForUnderInjection() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal("100"),
            actualGeneration = BigDecimal("90"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.upto10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "0",
            actual = result.beyond10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.totalDeviationCharge
        )
    }

    @Test
    fun shouldCalculateAggregatedChargesForDrawal() {

        val mapper = PspCalculationMapper()

        val input = Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = LocalDate.of(2026, 5, 25),
                time = LocalTime.of(0, 15)
            ),
            scheduledGeneration = BigDecimal.ZERO,
            actualGeneration = BigDecimal("-10"),
            frequency = BigDecimal("50.00"),
            rate = BigDecimal.ZERO,
            ppaRate = BigDecimal("4")
        )

        val result = mapper.map(input)

        //println("upto10PercentCharge = ${result.upto10PercentCharge}")
       // println("beyond10PercentCharge = ${result.beyond10PercentCharge}")
       // println("totalDeviationCharge = ${result.totalDeviationCharge}")

        assertBigDecimalEquals(
            expected = "0",
            actual = result.upto10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.beyond10PercentCharge
        )

        assertBigDecimalEquals(
            expected = "-400",
            actual = result.totalDeviationCharge
        )
    }

}