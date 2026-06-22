package com.example.GridSync.presentation.dsm.generalsellerdsm.domain

import java.io.File

sealed interface GenerateAp01OutputResult {

    data class Success(
        val outputFile: File
    ) : GenerateAp01OutputResult

    data class Failure(
        val message: String
    ) : GenerateAp01OutputResult
}