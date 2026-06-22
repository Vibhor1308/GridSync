package com.example.GridSync.presentation.dsm.generalsellerdsm

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GridSync.presentation.dsm.common.FileMetadata
import com.example.GridSync.presentation.dsm.common.validation.ValidationContext
import com.example.GridSync.presentation.dsm.common.validation.ValidationResult
import com.example.GridSync.presentation.dsm.generalsellerdsm.domain.GenerateAp01OutputUseCase
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.output.Ap01WorkbookGenerator
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01CsvFileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.configuration.GeneralSellerConfigurationProvider
import com.example.GridSync.presentation.dsm.generalsellerdsm.domain.GenerateAp01OutputResult
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerProject
import com.example.GridSync.presentation.dsm.generalsellerdsm.model.GeneralSellerUiState
import com.example.GridSync.presentation.dsm.generalsellerdsm.validation.GeneralSellerValidatorFactory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class GeneralSellerViewModel : ViewModel() {
    companion object{
        private const val TAG = "GeneralSellerViewModel"
    }

    private val _events =
        MutableSharedFlow<GeneralSellerEvent>()

    val events =
        _events.asSharedFlow()

    private val generateAp01OutputUseCase by lazy {
        GenerateAp01OutputUseCase(
            fileReader = Ap01CsvFileReader(),
            pspCalculationMapper = PspCalculationMapper(),
            workbookGenerator = Ap01WorkbookGenerator()
        )
    }

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

    fun runValidation(
        fileName: String,
        metadata: FileMetadata,
        selectedStartDate: LocalDate?,
        selectedEndDate: LocalDate?
    ): List<ValidationResult> {

        val project =
            uiState.value.selectedProject
                ?: return emptyList()

        val configuration =
            GeneralSellerConfigurationProvider
                .getConfiguration(project)

        val validationContext = ValidationContext(
            fileName = fileName,
            recordCount = metadata.rowCount,
            headers = metadata.headers,
            selectedStartDate =
                selectedStartDate,
            selectedEndDate =
                selectedEndDate,
            detectedStartDate =
                metadata.detectedStartDate,
            detectedEndDate =
                metadata.detectedEndDate,
            configuration = configuration,
            timeBlocks = metadata.timeBlocks
        )

        Log.d(
            TAG,
            "ValidationContext blocks=${metadata.timeBlocks.size}"
        )

        val validator =
            GeneralSellerValidatorFactory
                .getValidator(project)

        return validator.validate(
            validationContext
        )
    }

    fun clearSelection() {

        _uiState.value =
            _uiState.value.copy(
                selectedProject = null
            )
    }

    fun generateAp01Output(
        context: Context,
        inputFileUri: Uri
    ) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isGeneratingOutput = true)

            when (
                val result =
                    generateAp01OutputUseCase(
                        context = context,
                        inputFileUri = inputFileUri
                    )
            ) {

                is GenerateAp01OutputResult.Success -> {

                    _events.emit(
                        GeneralSellerEvent.ShareWorkbook(
                            outputFile = result.outputFile
                        )
                    )
                }

                is GenerateAp01OutputResult.Failure -> {

                    _events.emit(
                        GeneralSellerEvent.ShowError(
                            message = result.message
                        )
                    )
                }
            }

            _uiState.value = _uiState.value.copy(isGeneratingOutput = false)
        }
    }
}