import io.kotest.core.Tuple3
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value
import stringconcat.CreditCardNumberGenerator
import stringconcat.validateMirCardNumber

class MirCardNumberValidationWithJsPropertyTest : StringSpec({

    val context: Context = Context.create()
    val script = MirCardNumberValidationWithJsPropertyTest::class.java.getResource("mir.js")?.readText()
    context.eval("js", script)
    val jsFunction: Value = context.getBindings("js").getMember("validateMirCardNumber")

    val validGenerator = Arb.bind(
        Arb.int(220000, 220499),
        Arb.int(16, 19)
    ) { bin, len ->
        CreditCardNumberGenerator.generate(bin.toString(), len)
    }

    val invalidLengthGenerator = Arb.bind(
        Arb.int(220000, 220499),
        Arb.int(0, 15).merge(Arb.int(min = 20, max = 1000))
    ) { bin, len ->
        CreditCardNumberGenerator.generate(bin.toString(), len)
    }

    val invalidCharacterGenerator = Arb.string(16, 19, Codepoint.printableAscii())
    val invalidBinGenerator = Arb.bind(
        Arb.int(0, 219999).merge(Arb.int(min = 220500, max = 999999)).map { "%06d".format(it) },
        Arb.int(16, 19)
    )
    { bin, len ->
        CreditCardNumberGenerator.generate(bin, len)
    }

    val invalidChecksumGenerator = Arb.bind(
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
    "check equality" {
        val generator = validGenerator
            .merge(invalidLengthGenerator)
            .merge(invalidCharacterGenerator)
            .merge(invalidChecksumGenerator)
            .merge(invalidBinGenerator)

        checkAll(generator) { number ->
            val kotlinResult = validateMirCardNumber(number)
            val jsResult = jsFunction.execute(number)

            kotlinResult.fold(ifLeft = {
                jsResult.toString() shouldBe "{left: \"${it::class.simpleName}\"}"
            }, ifRight = {
                jsResult.toString() shouldBe "{right: undefined}"
            })
        }
    }
})

