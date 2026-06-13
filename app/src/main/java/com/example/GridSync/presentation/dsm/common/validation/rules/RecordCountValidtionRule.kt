package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object RecordCountValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        return if (
            context.recordCount ==
            context.configuration.expectedRecordCount
        ) {

            ValidationResult(
                validationName = "Record Count Validation",
                status = ValidationStatus.PASS,
                message =
                    "Expected ${context.configuration.expectedRecordCount} records found"
            )

        } else {

            ValidationResult(
                validationName = "Record Count Validation",
                status = ValidationStatus.FAIL,
                message =
                    "Expected ${context.configuration.expectedRecordCount}, found ${context.recordCount}"
            )
        }
    }
}