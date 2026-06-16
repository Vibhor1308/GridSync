package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation

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

        return PspCalculationRecord(
            input = input,

            deviation = deviation,
            deviationPercentage = deviationPercentage,

            limit1 = limit1,
            deviationLevel1 = deviationLevel1,
            deviationLevel2 = deviationLevel2,

            frequencyClass = frequencyClass,
            injectionType = injectionType,

            overInjectionChargeLevel1 = BigDecimal.ZERO,
            overInjectionChargeLevel2 = BigDecimal.ZERO,

            underInjectionChargeLevel1 = BigDecimal.ZERO,
            underInjectionChargeLevel2 = BigDecimal.ZERO,

            drawalCharge = BigDecimal.ZERO,

            totalOverInjectionCharge = BigDecimal.ZERO,
            totalUnderInjectionCharge = BigDecimal.ZERO
        )
    }
}