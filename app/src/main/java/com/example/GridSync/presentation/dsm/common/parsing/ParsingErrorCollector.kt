package com.example.GridSync.presentation.dsm.common.parsing

class ParsingErrorCollector {

    private val _errors = mutableListOf<ParsingError>()

    val errors: List<ParsingError>
        get() = _errors

    fun add(error: ParsingError) {
        _errors += error
    }

    fun hasErrors(): Boolean {
        return _errors.isNotEmpty()
    }
}