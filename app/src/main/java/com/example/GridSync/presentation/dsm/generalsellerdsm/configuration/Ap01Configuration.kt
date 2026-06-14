package com.example.GridSync.presentation.dsm.generalsellerdsm.configuration

import com.example.GridSync.presentation.dsm.common.configuration.ProjectConfiguration

object Ap01Configuration {

    val config = ProjectConfiguration(

        expectedFileNamePatterns = listOf(
            "greenko_kurnool_psp_firm"
        ),

        expectedRecordCount = 672,

        expectedBlocksPerDay = 96,

        requiredColumns = setOf(
            "date",
            "time",
            "sch_total",
            "act_total",
            "frequency",
            "rate",
            "ppa_rate"
        )
    )
}
