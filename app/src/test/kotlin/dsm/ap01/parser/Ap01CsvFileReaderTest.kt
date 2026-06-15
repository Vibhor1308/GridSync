package dsm.ap01.parser

import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01CsvFileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01FileReader
import com.example.GridSync.presentation.dsm.generalsellerdsm.ap01.parser.Ap01ParseResult
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.File
import kotlin.test.DefaultAsserter.fail

class Ap01CsvFileReaderTest {

    @Test
    fun shouldParseValidAp01File(){

        val reader: Ap01FileReader = Ap01CsvFileReader()

        val result = reader.read(
            File("src/test/resources/ap01-test-file.csv")
        )

        when (result) {

            is Ap01ParseResult.Success -> {
                assertEquals(
                    672,
                    result.records.size
                )
            }

            is Ap01ParseResult.Failure -> {
                fail(
                    "Expected success but got ${result.errors.size} errors"
                )
            }
        }
    }
}