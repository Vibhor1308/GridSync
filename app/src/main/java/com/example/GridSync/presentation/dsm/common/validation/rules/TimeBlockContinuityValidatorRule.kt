package com.example.GridSync.presentation.dsm.common.validation.rules

import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.ValidationStatus

object TimeBlockContinuityValidationRule {

    fun validate(
        context: ValidationContext
    ): ValidationResult {

        if (context.timeBlocks.isEmpty()) {

            return ValidationResult(
                validationName =
                    "Time Block Continuity Validation",

                status =
                    ValidationStatus.FAIL,

                message =
                    "No time blocks available"
            )
        }

        val discontinuities =
            mutableListOf<String>()

        context.timeBlocks
            .groupBy { it.date }
            .forEach { (date, blocks) ->

                val sortedBlocks =
                    blocks.sortedBy {
                        it.time
                    }

                for (i in 1 until sortedBlocks.size) {

                    val previous =
                        sortedBlocks[i - 1]

                    val current =
                        sortedBlocks[i]

                    val expectedTime =
                        previous.time.plusMinutes(15)

                    if (
                        current.time != expectedTime
                    ) {

                        discontinuities.add(
                            "$date : Expected $expectedTime but found ${current.time}"
                        )
                    }
                }
            }

        return if (
            discontinuities.isEmpty()
        ) {

            ValidationResult(
                validationName =
                    "Time Block Continuity Validation",

                status =
                    ValidationStatus.PASS,

                message =
                    "All time blocks are continuous"
            )

        } else {

            ValidationResult(
                validationName =
                    "Time Block Continuity Validation",

                status =
                    ValidationStatus.FAIL,

                message =
                    discontinuities.take(10)
                        .joinToString("\n")
            )
        }
    }
}