package stringconcat

import kotlin.random.Random


object CreditCardNumberGenerator {

    fun generate(bin: String, length:Int): String {

        val randomDigits = (1 until length - bin.length).joinToString("") {
            Random.nextInt(0, 10).toString()
        }

        val cardNumberWithoutChecksum = bin + randomDigits
        val checksum = calculateLuhnChecksum(cardNumberWithoutChecksum)
        return cardNumberWithoutChecksum + checksum
    }

    fun calculateLuhnChecksum(number: String): Int {
        val digits = number.map { it.toString().toInt() }
        val sum = digits.reversed().mapIndexed { index, digit ->
            if (index % 2 == 0) {
                val doubled = digit * 2
                if (doubled > 9) doubled - 9 else doubled
            } else {
                digit
            }
        }.sum()

        return (10 - (sum % 10)) % 10
    }
}