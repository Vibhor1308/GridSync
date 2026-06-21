package com.example.GridSync.presentation.dsm.generalsellerdsm.domain

import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01FileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01ParseResult
import java.io.File

class ProcessAp01FileUseCase(
    private val csvFileReader: Ap01FileReader,
    private val pspCalculationMapper: PspCalculationMapper
) {

    suspend operator fun invoke(
        file: File
    ): ProcessAp01FileResult {

        return when (
            val parseResult = csvFileReader.read(file)
        ) {

            is Ap01ParseResult.Failure -> {
                ProcessAp01FileResult.Failure(
                    errors = parseResult.errors
                )
            }

            is Ap01ParseResult.Success -> {

                val calculationRecords =
                    parseResult.records.map {
                        pspCalculationMapper.map(it)
                    }

                ProcessAp01FileResult.Success(
                    inputRecords = parseResult.records,
                    calculationRecords = calculationRecords
                )
            }
        }
    }
}