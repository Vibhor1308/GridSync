package com.example.GridSync.presentation.dsm.generalsellerdsm.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.calculation.PspCalculationMapper
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.output.Ap01WorkbookGenerator
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01FileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01ParseResult
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

class GenerateAp01OutputUseCase(
    private val fileReader: Ap01FileReader,
    private val pspCalculationMapper: PspCalculationMapper,
    private val workbookGenerator: Ap01WorkbookGenerator
){
    companion object{
        private const val TAG = "GenerateAp01OutputUseCase"
    }

    suspend operator fun invoke(
        context: Context,
        inputFileUri: Uri
    ): GenerateAp01OutputResult {

        return try {

            val inputFile = copyUriToTempFile(
                context = context,
                uri = inputFileUri
            )

            val parseResult = fileReader.read(inputFile)

            when (parseResult) {

                is Ap01ParseResult.Failure -> {

                    val errorMessages = parseResult.errors.joinToString("\n") {
                        it.toHumanReadableMessage()
                    }

                    Log.e(TAG, "Parsing failed with the following errors:\n$errorMessages")

                    GenerateAp01OutputResult.Failure(
                        message = errorMessages
                    )
                }

                is Ap01ParseResult.Success -> {

                    val calculationRecords =
                        parseResult.records.map { inputRecord ->
                            pspCalculationMapper.map(inputRecord)
                        }

                    val workbook =
                        loadTemplateWorkbook(context)

                    workbookGenerator.generate(
                        workbook = workbook,
                        inputRecords = parseResult.records,
                        calculationRecords = calculationRecords
                    )

                    val outputFile =
                        createOutputFile(context)

                    outputFile.outputStream().use { outputStream ->
                        workbook.write(outputStream)
                    }

                    workbook.close()

                    Log.d(
                        TAG,
                        "Workbook generated at ${outputFile.absolutePath}"
                    )

                    GenerateAp01OutputResult.Success(
                        outputFile = outputFile
                    )
                }
            }

        } catch (exception: Exception) {

            GenerateAp01OutputResult.Failure(
                message = exception.message ?: "Unknown error"
            )
        }
    }

    private fun copyUriToTempFile(
        context: Context,
        uri: Uri
    ): File {

        val tempFile = File(
            context.cacheDir,
            "ap01_input.csv"
        )

        context.contentResolver
            .openInputStream(uri)
            ?.use { input ->

                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

        return tempFile
    }

    private fun loadTemplateWorkbook(
        context: Context
    ): Workbook {

        return context.assets.open(
            "ap_01_injection_output_format.xlsx"
        ).use { inputStream ->

            WorkbookFactory.create(inputStream)
        }
    }

    private fun createOutputFile(
        context: Context
    ): File {

        val outputDirectory = File(
            context.filesDir,
            "generated_reports"
        )

        outputDirectory.mkdirs()

        return File(
            outputDirectory,
            "AP01_Output.xlsx"
        )
    }
}