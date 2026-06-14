package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object DuplicateDateTimeValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        if (context.timeBlocks.isEmpty()) {

            return ValidationResult(
                validationName =
                    "Duplicate Time Block Validation",

                status =
                    ValidationStatus.FAIL,

                message =
                    "No time blocks available"
            )
        }

        val duplicates =

            context.timeBlocks
                .groupBy { it }
                .filterValues {
                    it.size > 1
                }

        return if (
            duplicates.isEmpty()
        ) {

            ValidationResult(
                validationName =
                    "Duplicate Time Block Validation",

                status =
                    ValidationStatus.PASS,

                message =
                    "No duplicate time blocks found"
            )

        } else {

            val duplicateSummary =

                duplicates.keys
                    .sortedWith(
                        compareBy<DsmTimeBlock> {
                            it.date
                        }.thenBy {
                            it.time
                        }
                    )
                    .take(10)
                    .joinToString("\n") {

                        "${it.date} ${it.time}"
                    }

            ValidationResult(
                validationName =
                    "Duplicate Time Block Validation",

                status =
                    ValidationStatus.FAIL,

                message =
                    buildString {

                        appendLine(
                            "Found ${duplicates.size} duplicate time block(s)"
                        )

                        appendLine()

                        append(
                            duplicateSummary
                        )
                    }
            )
        }
    }
}