package dsm.common.frequency

import com.example.GridSync.presentation.dsm.common.frequency.FrequencyClass
import com.example.GridSync.presentation.dsm.common.frequency.FrequencyClassifier
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class FrequencyClassifierTest {
    @Test
    fun shouldClassifyFrequencyCorrectly() {

        assertEquals(
            FrequencyClass.UnderFrequency1,
            FrequencyClassifier.classify(BigDecimal("49.90"))
        )

        assertEquals(
            FrequencyClass.UnderFrequency2,
            FrequencyClassifier.classify(BigDecimal("49.89"))
        )

        assertEquals(
            FrequencyClass.UnderFrequency,
            FrequencyClassifier.classify(BigDecimal("49.91"))
        )

        assertEquals(
            FrequencyClass.BAND,
            FrequencyClassifier.classify(BigDecimal("49.97"))
        )

        assertEquals(
            FrequencyClass.BAND,
            FrequencyClassifier.classify(BigDecimal("50.03"))
        )

        assertEquals(
            FrequencyClass.OverFrequency,
            FrequencyClassifier.classify(BigDecimal("50.04"))
        )

        assertEquals(
            FrequencyClass.OverFrequency,
            FrequencyClassifier.classify(BigDecimal("50.05"))
        )

        assertEquals(
            FrequencyClass.OverFrequency1,
            FrequencyClassifier.classify(BigDecimal("50.06"))
        )

        assertEquals(
            FrequencyClass.OverFrequency2,
            FrequencyClassifier.classify(BigDecimal("50.10"))
        )
    }
}