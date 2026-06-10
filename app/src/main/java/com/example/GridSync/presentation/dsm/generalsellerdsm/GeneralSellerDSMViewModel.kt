package com.example.GridSync.presentation.dsm.generalsellerdsm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GeneralSellerDsmViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            GeneralSellerDsmUiState()
        )

    val uiState = _uiState.asStateFlow()
}