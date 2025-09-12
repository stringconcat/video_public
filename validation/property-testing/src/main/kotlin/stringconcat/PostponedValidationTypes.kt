package stringconcat

import arrow.core.left
import arrow.core.right

sealed class Address(val address: String) {
    class ValidAddress(address: String) : Address(address)

    class InvalidAddress(address: String, val reason: String) : Address(address) {
        fun markAsValid() = ValidAddress(address)
    }

    companion object {
        fun from(address: String) =
            if (address.matches(Regex.fromLiteral("$\\w+"))) {
                ValidAddress(address).right()
            } else {
                InvalidAddress(address, reason = "some reason").left()
            }
    }
}

sealed class OrderNumber(val number: String) {
    class ValidOrder(number: String) : OrderNumber(number)

    class InvalidOrder(number: String, reason: String) : OrderNumber(number) {
        fun markAsValid() = ValidOrder(number)
    }
}
