package com.example.GridSync.presentation.dsm.common.validation

import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

data class ValidationResult(
    val validationName: String,
    val status: ValidationStatus,
    val message: String
)