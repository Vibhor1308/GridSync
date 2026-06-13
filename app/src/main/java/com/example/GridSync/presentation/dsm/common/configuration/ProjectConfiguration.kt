package com.example.GridSync.presentation.dsm.common.configuration

data class ProjectConfiguration(
    val expectedFileNamePatterns: List<String>,
    val expectedRecordCount: Int,
    val expectedBlocksPerDay: Int,
    val requiredColumns: Set<String>
)
