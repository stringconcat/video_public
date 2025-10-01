package stringconcat

import arrow.core.left
import arrow.core.right

data class Email internal constructor(private val email: String) {

    init {
        check(isValid(email))
    }

    companion object {
        private val REGEX =
            Regex("^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$")

        fun from(email: String) =
            if (isValid(email)) {
                Email(email).right()
            } else {
                InvalidFormatError.left()
            }

        private fun isValid(email: String) = REGEX.matches(email)
    }
}

object InvalidFormatError

