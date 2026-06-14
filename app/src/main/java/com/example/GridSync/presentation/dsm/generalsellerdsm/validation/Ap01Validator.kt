package com.example.GridSync.presentation.dsm.generalsellerdsm.validation

import com.example.GridSync.presentation.dsm.common.validation.DsmValidator
import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.common.validation.rules.FileNameValidationRule
import com.example.GridSync.presentation.dsm.common.validation.rules.RecordCountValidationRule
import com.example.GridSync.presentation.dsm.common.validation.rules.RequiredColumnsValidationRule

class Ap01Validator: DsmValidator {

    override fun validate(context: ValidationContext): List<ValidationResult> {
        return listOf(

            FileNameValidationRule.validate(
                context
            ),

            RecordCountValidationRule.validate(
                context
            ),

            RequiredColumnsValidationRule.validate(
                context
            )

        )
    }
}