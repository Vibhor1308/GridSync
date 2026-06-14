package com.example.GridSync.presentation.dsm.common.validation.rules


import android.util.Log
import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object RecordsPerDayValidationRule {

    private const val TAG = "RecordsPerDayValidationRule"

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        if (context.timeBlocks.isEmpty()) {
            return ValidationResult(
                validationName =
                    "Records Per Day Validation",
                status =
                    ValidationStatus.FAIL,
                message =
                    "No time blocks available for validation"
            )
        }

        val invalidDays =

            context.timeBlocks.groupBy { it.date }.filterValues { blocks ->

                    blocks.size != context.configuration.expectedBlocksPerDay
                }

        return if (invalidDays.isEmpty()) {
        Log.d(TAG,"[records per day validation -> pass]")
            ValidationResult(
                validationName = "Records Per Day Validation",

                status = ValidationStatus.PASS,

                message = "All days contain ${
                    context.configuration.expectedBlocksPerDay
                } records"
            )

        } else {
            Log.d(TAG,"[records per day validation -> pass]")
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