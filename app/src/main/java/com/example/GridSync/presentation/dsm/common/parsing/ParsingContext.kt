package com.example.GridSync.presentation.dsm.common.parsing

data class ParsingContext(
    val rowNumber: Int,
    val errorCollector: ParsingErrorCollector
)
