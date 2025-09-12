import arrow.core.right
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import stringconcat.validateMirCardNumber

class MirCardNumberValidationUnitTest {

    @Test
    fun `correct format`() {
        val number = "2200532979831431"
        val result = validateMirCardNumber(number)
        result.shouldBe(Unit.right())
    }

    @Test
    fun `incorrect format`() {
        val number = "234124"
        val result = validateMirCardNumber(number)
        result.shouldNotBe(Unit.right())
    }
}

