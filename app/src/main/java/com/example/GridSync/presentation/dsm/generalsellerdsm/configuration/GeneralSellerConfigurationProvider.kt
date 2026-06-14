package com.example.GridSync.presentation.dsm.generalsellerdsm.configuration

import com.example.GridSync.presentation.dsm.common.configuration.ProjectConfiguration
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject

object GeneralSellerConfigurationProvider {

    fun getConfiguration(
        project: GeneralSellerProject
    ): ProjectConfiguration {

        return when(project) {

            GeneralSellerProject.AP01_INJECTION ->
                Ap01Configuration.config

            GeneralSellerProject.DIGCHU ->
                DigchuConfiguration.config

            GeneralSellerProject.BUDDHIL ->
                BuddhilConfiguration.config
        }
    }
}