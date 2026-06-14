package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object RecordsPerDayValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        val invalidDays =

            context.timeBlocks.groupBy { it.date }.filterValues { blocks ->

                    blocks.size != context.configuration.expectedBlocksPerDay
                }

        return if (invalidDays.isEmpty()) {

            ValidationResult(
                validationName = "Records Per Day Validation",

                status = ValidationStatus.PASS,

                message = "All days contain ${
                    context.configuration.expectedBlocksPerDay
                } records"
            )

        } else {

            ValidationResult(
                validationName = "Records Per Day Validation",

                status = ValidationStatus.FAIL,

                message = invalidDays.entries.joinToString(
                        separator = "\n"
                    ) {

                        "${it.key} : ${it.value.size} records"
                    })
        }
    }
}