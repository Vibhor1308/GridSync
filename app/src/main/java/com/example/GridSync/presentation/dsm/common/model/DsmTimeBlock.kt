package com.example.GridSync.presentation.dsm.common.model

import java.time.LocalDate
import java.time.LocalTime

data class DsmTimeBlock(

    val date: LocalDate,

    val time: LocalTime
) {

    val dateTimeKey: String
        get() = "$date|$time"
}
