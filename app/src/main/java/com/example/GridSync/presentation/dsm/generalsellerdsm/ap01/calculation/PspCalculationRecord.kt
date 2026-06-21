package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation

import com.example.GridSync.presentation.dsm.common.frequency.FrequencyClass
import com.example.GridSync.presentation.dsm.common.injection.InjectionType
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import java.math.BigDecimal

data class PspCalculationRecord(

    val input: Ap01InputRecord,

    val deviation: BigDecimal,
    val deviationPercentage: BigDecimal,

    val limit1: BigDecimal,
    val deviationLevel1: BigDecimal,
    val deviationLevel2: BigDecimal,

    val frequencyClass: FrequencyClass,
    val injectionType: InjectionType,

    val overInjectionChargeLevel1: BigDecimal,
    val overInjectionChargeLevel2: BigDecimal,

    val underInjectionChargeLevel1: BigDecimal,
    val underInjectionChargeLevel2: BigDecimal,

    val drawalCharge: BigDecimal,

    val totalOverInjectionCharge: BigDecimal,
    val totalUnderInjectionCharge: BigDecimal,

    val upto10PercentCharge: BigDecimal,
    val beyond10PercentCharge: BigDecimal,
    val totalDeviationCharge: BigDecimal
)
