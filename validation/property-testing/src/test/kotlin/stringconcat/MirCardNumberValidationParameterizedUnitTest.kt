package stringconcat

import arrow.core.right
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MirCardNumberValidationParameterizedUnitTest {

    @ParameterizedTest
    @ValueSource(
        strings = arrayOf(
            "2200294310101145",
            "2200291728418830",
            "2200292613998522",
            "2200297782242479",
            "2200778568759190",
            "2200774114422391",
            "2200777682872582"
        )
    )
    fun `correct formats`(number: String) {
        val result = validateMirCardNumber(number)
        result.shouldBe(Unit.right())
    }

    @ParameterizedTest
    @ValueSource(
        strings = arrayOf(
            "2200294310101144",
            "2200291728418831",
            "2200292613998523",
            "2200297782242478",
            "2500778568759190",
            "2200774114",
            "220077768287258223423423"
        )
    )
    fun `incorrect formats`(number: String) {
        val result = validateMirCardNumber(number)
        result.shouldNotBe(Unit.right())
    }
}