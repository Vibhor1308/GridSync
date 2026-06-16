package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser

import com.example.GridSync.presentation.dsm.common.parsing.ParsingError
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord

sealed interface Ap01ParseResult {

    data class Success(
        val records: List<Ap01InputRecord>
    ) : Ap01ParseResult

    data class Failure(
        val errors: List<ParsingError>
    ) : Ap01ParseResult
}