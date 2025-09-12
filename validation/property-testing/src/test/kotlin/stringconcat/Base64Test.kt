package stringconcat

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@ExperimentalEncodingApi
class Base64Test:  StringSpec({

    "round trip" {
        val generator = Arb.byteArray(Arb.nonNegativeInt(10000), Arb.byte())
        checkAll(generator) { src ->
            val encoded = Base64.encode(src)
            val decoded = Base64.decode(encoded)
            src shouldBeSameSizeAs decoded
        }
    }
})