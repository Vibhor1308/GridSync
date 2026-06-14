package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object DateRangeValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        val matches =

            context.selectedStartDate ==
                    context.detectedStartDate &&

                    context.selectedEndDate ==
                    context.detectedEndDate

        return if (matches) {

            ValidationResult(
                validationName = "Date Range Validation",

                status = ValidationStatus.PASS,

                message =
                    "Selected date range matches uploaded file"
            )

        } else {

            ValidationResult(
                validationName = "Date Range Validation",

                status = ValidationStatus.FAIL,

                message =
                    """
                    Expected:
                    ${context.selectedStartDate}
                    to
                    ${context.selectedEndDate}

                    Found:
                    ${context.detectedStartDate}
                    to
                    ${context.detectedEndDate}
                    """.trimIndent()
            )
        }
    }
}