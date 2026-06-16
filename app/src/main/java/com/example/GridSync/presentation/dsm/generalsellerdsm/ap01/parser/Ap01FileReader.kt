package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser

import java.io.File

interface Ap01FileReader {

    suspend fun read(
        file: File
    ): Ap01ParseResult
}