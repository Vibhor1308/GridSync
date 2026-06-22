package com.example.GridSync.presentation.dsm.generalsellerdsm

import java.io.File

sealed interface GeneralSellerEvent {

    data class ShareWorkbook(
        val outputFile: File
    ) : GeneralSellerEvent

    data class ShowError(
        val message: String
    ) : GeneralSellerEvent
}