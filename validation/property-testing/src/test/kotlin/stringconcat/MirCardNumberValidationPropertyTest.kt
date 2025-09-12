import arrow.core.left
import io.kotest.core.Tuple3
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import stringconcat.CreditCardNumberGenerator
import stringconcat.ValidationError
import stringconcat.validateMirCardNumber

class MirCardNumberValidationPropertyTest : StringSpec({

    "valid Mir card numbers should pass validation" {
        val generator = Arb.bind(
            Arb.int(220000, 220499),
            Arb.int(16, 19)
        ) { bin, len ->
            Pair(bin, len)
        }

        checkAll(generator) {
            val number = CreditCardNumberGenerator.generate(it.first.toString(), it.second)
            println(number)
            validateMirCardNumber(number).isRight() shouldBe true
        }
    }

    "invalid length card numbers should fail with InvalidLength" {
        val generator = Arb.bind(
            Arb.int(220000, 220499),
            Arb.int(0, 15).merge(Arb.int(min = 20, max = 1000))
        )
        { bin, len ->
            Pair(bin, len)
        }

        checkAll(generator) {
            val number = CreditCardNumberGenerator.generate(it.first.toString(), it.second)
            validateMirCardNumber(number) shouldBe ValidationError.InvalidLength.left()
        }
    }

    "invalid characters card numbers should fail with InvalidCharacters" {

        val generator = Arb.string(16, 19, Codepoint.printableAscii())
        checkAll(generator) { cardNumber ->
            validateMirCardNumber(cardNumber) shouldBe ValidationError.InvalidCharacters.left()
        }
    }

    "invalid BIN card numbers should fail with InvalidBin" {
        val generator = Arb.bind(
            Arb.int(0, 219999).merge(Arb.int(min = 220500, max = 999999)).map { "%06d".format(it) },
            Arb.int(16, 19)
        )
        { bin, len ->
            Pair(bin, len)
        }

        checkAll(generator) {
            val number = CreditCardNumberGenerator.generate(it.first, it.second)
            validateMirCardNumber(number) shouldBe ValidationError.InvalidBin.left()
        }
    }

    "invalid checksum card numbers should fail with InvalidChecksum" {
        val generator = Arb.bind(
            Arb.int(220000, 220499),
            Arb.int(16, 19),
            Arb.int(1, 9)
        ) { bin, len, checksum ->
            Tuple3(bin, len, checksum)
        }.map {
            val number = CreditCardNumberGenerator.generate(it.a.toString(), it.b)
            val last = number.last().toString().toInt()
            val newLast = (last + it.c) % 10
            number.dropLast(1) + newLast.toString()
        }

        checkAll(generator) { number ->
            validateMirCardNumber(number) shouldBe ValidationError.InvalidChecksum.left()
        }
    }

    "valid characters card numbers should not fail with InvalidCharacters" { // инвертированное свойство
        val generator = Arb.list(Arb.int(0, 9)).map {
            it.joinToString("")
        }
        checkAll(generator) { cardNumber ->
            validateMirCardNumber(cardNumber) shouldNotBe ValidationError.InvalidCharacters.left()
        }
    }
})

