package com.example.GridSync.presentation.dsm.generalsellerdsm.domain

import com.example.GridSync.presentation.dsm.common.parsing.ParsingError
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationRecord
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord

sealed interface ProcessAp01FileResult {

    data class Success(
        val inputRecords: List<Ap01InputRecord>,
        val calculationRecords: List<PspCalculationRecord>
    ) : ProcessAp01FileResult

    data class Failure(
        val errors: List<ParsingError>
    ) : ProcessAp01FileResult
}