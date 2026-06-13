package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object FileNameValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        val matches =
            context.configuration
                .expectedFileNamePatterns
                .any { pattern ->

                    context.fileName.contains(
                        pattern,
                        ignoreCase = true
                    )
                }

        return if (matches) {

            ValidationResult(
                validationName = "File Name Validation",
                status = ValidationStatus.PASS,
                message = "File name matches expected pattern"
            )

        } else {

            ValidationResult(
                validationName = "File Name Validation",
                status = ValidationStatus.FAIL,
                message = "File name does not match expected pattern"
            )
        }
    }
}