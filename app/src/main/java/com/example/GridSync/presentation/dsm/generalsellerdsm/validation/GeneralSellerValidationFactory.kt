package com.example.GridSync.presentation.dsm.generalsellerdsm.validation

import com.example.GridSync.presentation.dsm.common.validation.DsmValidator
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject

object GeneralSellerValidatorFactory {

    fun getValidator(
        project: GeneralSellerProject
    ): DsmValidator {

        return when(project) {

            GeneralSellerProject.AP01_INJECTION ->
                Ap01Validator()

            GeneralSellerProject.DIGCHU ->
                DigchuValidator()

            GeneralSellerProject.BUDDHIL ->
                BuddhilValidator()
        }
    }
}