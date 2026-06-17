package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation

import com.example.GridSync.presentation.common.Constants
import com.example.GridSync.presentation.dsm.common.frequency.FrequencyClass
import com.example.GridSync.presentation.dsm.common.frequency.FrequencyClassifier
import com.example.GridSync.presentation.dsm.common.injection.InjectionClassifier
import com.example.GridSync.presentation.dsm.common.injection.InjectionType
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import java.math.BigDecimal
import java.math.RoundingMode

class PspCalculationMapper {
    companion object{
            private val TEN_PERCENT = BigDecimal("0.10")
            private val TWENTY_FIVE = BigDecimal("25")
    }
    fun map(
        input: Ap01InputRecord
    ): PspCalculationRecord{
        val deviation =
            input.actualGeneration
                .subtract(input.scheduledGeneration)

        val deviationPercentage =
            if (input.scheduledGeneration.compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal.ZERO
            } else {
                deviation.divide(
                    input.scheduledGeneration,
                    6,
                    RoundingMode.HALF_UP
                )
            }

        val limit1 =
            input.scheduledGeneration
                .multiply(TEN_PERCENT)
                .min(TWENTY_FIVE)

        val deviationLevel1 =
            deviation.abs()
                .min(limit1)

        val deviationLevel2 =
            deviation.abs()
                .subtract(limit1)
                .max(BigDecimal.ZERO)

        val frequencyClass =
            FrequencyClassifier.classify(
                input.frequency
            )

        val injectionType =
            InjectionClassifier.classifyInjectionType(
                deviation = deviation
            )

        val overInjectionChargeLevel1 =
            calculateOverInjectionChargeLevel1(
                frequencyClass = frequencyClass,
                injectionType = injectionType,
                frequency = input.frequency,
                ppaRate = input.ppaRate,
                deviationLevel1 = deviationLevel1
            )

        return PspCalculationRecord(
            input = input,

            deviation = deviation,
            deviationPercentage = deviationPercentage,

            limit1 = limit1,
            deviationLevel1 = deviationLevel1,
            deviationLevel2 = deviationLevel2,

            frequencyClass = frequencyClass,
            injectionType = injectionType,

            overInjectionChargeLevel1 = overInjectionChargeLevel1,
            overInjectionChargeLevel2 = BigDecimal.ZERO,

            underInjectionChargeLevel1 = BigDecimal.ZERO,
            underInjectionChargeLevel2 = BigDecimal.ZERO,

            drawalCharge = BigDecimal.ZERO,

            totalOverInjectionCharge = BigDecimal.ZERO,
            totalUnderInjectionCharge = BigDecimal.ZERO
        )
    }

    private fun calculateOverInjectionChargeLevel1(
        frequencyClass: FrequencyClass,
        injectionType: InjectionType,
        frequency: BigDecimal,
        ppaRate: BigDecimal,
        deviationLevel1: BigDecimal
    ): BigDecimal {

        if (injectionType != InjectionType.OverInjection) {
            return BigDecimal.ZERO
        }

        val baseCharge =
            deviationLevel1
                .multiply(ppaRate)
                .multiply(
                    Constants.PAISE_PER_KWH_TO_RUPEES_PER_MWH_CONVERSION_FACTOR
                )

        return when (frequencyClass) {

            FrequencyClass.BAND ->
                baseCharge

            FrequencyClass.OverFrequency ->
                calculateOverFrequencyCharge(
                    frequency = frequency,
                    baseCharge = baseCharge
                )

            FrequencyClass.UnderFrequency ->
                calculateUnderFrequencyCharge(
                    frequency = frequency,
                    ppaRate = ppaRate,
                    deviationLevel1 = deviationLevel1
                )

            FrequencyClass.OverFrequency1 ->
                BigDecimal.ZERO

            FrequencyClass.OverFrequency2 ->
                baseCharge.multiply(BigDecimal("-0.1"))

            FrequencyClass.UnderFrequency1,
            FrequencyClass.UnderFrequency2 ->
                baseCharge.multiply(BigDecimal("1.15"))
        }
    }

    private fun calculateOverFrequencyCharge(
        frequency: BigDecimal,
        baseCharge: BigDecimal
    ): BigDecimal {

        return when (frequency) {

            BigDecimal("50.04") ->
                baseCharge.multiply(BigDecimal("0.75"))

            BigDecimal("50.05") ->
                baseCharge.multiply(BigDecimal("0.50"))

            else ->
                BigDecimal.ZERO
        }
    }

    private fun calculateUnderFrequencyCharge(
        frequency: BigDecimal,
        ppaRate: BigDecimal,
        deviationLevel1: BigDecimal
    ): BigDecimal {

        val multiplier =
            BigDecimal("100")
                .add(
                    BigDecimal("49.97")
                        .subtract(frequency)
                        .multiply(BigDecimal("2.15"))
                        .multiply(BigDecimal("100"))
                )

        return multiplier
            .multiply(ppaRate)
            .multiply(deviationLevel1)
            .divide(
                Constants.PAISE_PER_KWH_TO_RUPEES_PER_MWH_CONVERSION_FACTOR,
                6,
                RoundingMode.HALF_UP
            )
    }
}