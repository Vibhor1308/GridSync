package com.example.GridSync.presentation.dsm.generalsellerdsm

import androidx.lifecycle.ViewModel
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GeneralSellerViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            GeneralSellerUiState()
        )

    val uiState = _uiState.asStateFlow()

    fun selectProject(
        project: GeneralSellerProject
    ) {

        _uiState.value =
            _uiState.value.copy(
                selectedProject = project
            )
    }

    fun clearSelection() {

        _uiState.value =
            _uiState.value.copy(
                selectedProject = null
            )
    }
}