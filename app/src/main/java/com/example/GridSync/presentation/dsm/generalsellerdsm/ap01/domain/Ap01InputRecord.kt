package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain

import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import java.math.BigDecimal

data class Ap01InputRecord(
    val timeBlock: DsmTimeBlock,
    val scheduledGeneration: BigDecimal,
    val actualGeneration: BigDecimal,
    val frequency: BigDecimal,
    val rate: BigDecimal,
    val ppaRate: BigDecimal
)
