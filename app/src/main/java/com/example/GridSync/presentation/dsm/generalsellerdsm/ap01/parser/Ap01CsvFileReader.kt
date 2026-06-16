package com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser

import com.example.GridSync.presentation.dsm.common.model.DsmTimeBlock
import com.example.GridSync.presentation.dsm.common.parsing.ParsingContext
import com.example.GridSync.presentation.dsm.common.parsing.ParsingErrorCollector
import com.example.GridSync.presentation.dsm.common.parsing.parseBigDecimal
import com.example.GridSync.presentation.dsm.common.parsing.parseDate
import com.example.GridSync.presentation.dsm.common.parsing.parseTime
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.domain.Ap01InputRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File

class Ap01CsvFileReader : Ap01FileReader {

    override suspend fun read(
        file: File
    ): Ap01ParseResult = withContext(Dispatchers.IO) {

        val collector = ParsingErrorCollector()
        val records = mutableListOf<Ap01InputRecord>()

        file.bufferedReader().use { reader ->

            val csvRecords = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader)

            csvRecords.forEachIndexed { index, record ->

                val rowNumber = index + 2

                val parsedRecord = parseRow(
                    record = record,
                    rowNumber = rowNumber,
                    collector = collector
                )

                if (parsedRecord != null) {
                    records += parsedRecord
                }

            }

        }

        return@withContext if (collector.hasErrors()) {
            Ap01ParseResult.Failure(
                errors = collector.errors
            )
        } else {
            Ap01ParseResult.Success(
                records = records
            )
        }
    }

    private fun parseRow(
        record: CSVRecord,
        rowNumber: Int,
        collector: ParsingErrorCollector
    ): Ap01InputRecord? {

        val context = ParsingContext(
            rowNumber = rowNumber,
            errorCollector = collector
        )

        val errorCountBeforeRow =
            collector.errors.size

        val date = parseDate(
            value = record[Ap01Columns.DATE],
            columnName = Ap01Columns.DATE,
            context = context
        )

        val time = parseTime(
            value = record[Ap01Columns.TIME],
            columnName = Ap01Columns.TIME,
            context = context
        )

        val scheduledGeneration = parseBigDecimal(
            value = record[Ap01Columns.SCHEDULED_GENERATION],
            columnName = Ap01Columns.SCHEDULED_GENERATION,
            context = context
        )

        val actualGeneration = parseBigDecimal(
            value = record[Ap01Columns.ACTUAL_GENERATION],
            columnName = Ap01Columns.ACTUAL_GENERATION,
            context = context
        )

        val frequency = parseBigDecimal(
            value = record[Ap01Columns.FREQUENCY],
            columnName = Ap01Columns.FREQUENCY,
            context = context
        )

        val rate = parseBigDecimal(
            value = record[Ap01Columns.RATE],
            columnName = Ap01Columns.RATE,
            context = context
        )

        val ppaRate = parseBigDecimal(
            value = record[Ap01Columns.PPA_RATE],
            columnName = Ap01Columns.PPA_RATE,
            context = context
        )

        val rowHasErrors =
            collector.errors.size > errorCountBeforeRow

        if (rowHasErrors) {
            return null
        }

        return Ap01InputRecord(
            timeBlock = DsmTimeBlock(
                date = date!!,
                time = time!!
            ),
            scheduledGeneration = scheduledGeneration!!,
            actualGeneration = actualGeneration!!,
            frequency = frequency!!,
            rate = rate!!,
            ppaRate = ppaRate!!
        )
    }
}
