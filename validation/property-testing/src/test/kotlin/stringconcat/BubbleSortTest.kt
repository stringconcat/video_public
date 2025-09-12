package stringconcat

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll

class BubbleSortTest:  StringSpec({

    "sort random values" {
        val generator = Arb.intArray(Arb.int(0, 1000), Arb.int())
        checkAll(generator) { src ->
            bubbleSort(src)
            for ((index, i) in src.withIndex()) {
                if(index == src.lastIndex) break
                i shouldBeLessThanOrEqualTo src[index+1]
            }
        }
    }

    "sort reversed values" {
        val generator = Arb.intArray(Arb.int(0, 1000), Arb.int())
            .map { it.sortedArrayDescending() }
        checkAll(generator) { src ->
            bubbleSort(src)
            for ((index, i) in src.withIndex()) {
                if(index == src.lastIndex) break
                i shouldBeLessThanOrEqualTo src[index+1]
            }
        }
    }
})