package com.example.GridSync.presentation.dsm.common.validation

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult

interface DsmValidator {

    fun validate(
        context: ValidationContext
    ): List<ValidationResult>

}