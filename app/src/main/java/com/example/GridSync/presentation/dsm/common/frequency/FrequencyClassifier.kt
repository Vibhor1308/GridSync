package com.example.GridSync.presentation.dsm.common.frequency

import java.math.BigDecimal

object FrequencyClassifier {

    fun classify(
        frequency: BigDecimal
    ): FrequencyClass {
        return when {

            frequency >= BigDecimal("49.97") &&
                    frequency <= BigDecimal("50.03") ->
                FrequencyClass.BAND

            frequency > BigDecimal("50.03") &&
                    frequency <= BigDecimal("50.05") ->
                FrequencyClass.OverFrequency

            frequency >= BigDecimal("49.91") &&
                    frequency < BigDecimal("49.97") ->
                FrequencyClass.UnderFrequency

            frequency > BigDecimal("50.05") &&
                    frequency < BigDecimal("50.10") ->
                FrequencyClass.OverFrequency1

            frequency >= BigDecimal("50.10") ->
                FrequencyClass.OverFrequency2

            frequency.compareTo(BigDecimal("49.90")) == 0 ->
                FrequencyClass.UnderFrequency1

            else ->
                FrequencyClass.UnderFrequency2
        }

    }
}