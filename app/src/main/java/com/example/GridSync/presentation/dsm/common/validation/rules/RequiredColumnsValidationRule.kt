package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object RequiredColumnsValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        val missingColumns =
            context.configuration.requiredColumns
                .filterNot { requiredColumn ->

                    context.normalizedHeaders.contains(
                        requiredColumn
                            .trim()
                            .lowercase()
                    )
                }

        return if (missingColumns.isEmpty()) {

            ValidationResult(
                validationName = "Required Columns Validation",
                status = ValidationStatus.PASS,
                message = "All required columns found"
            )

        } else {

            ValidationResult(
                validationName = "Required Columns Validation",
                status = ValidationStatus.FAIL,
                message =
                    "Missing columns: ${
                        missingColumns.joinToString()
                    }"
            )
        }
    }
}