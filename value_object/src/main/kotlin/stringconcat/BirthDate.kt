package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.time.Clock
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class BirthDate internal constructor(private val birthdate: LocalDate) {

    fun localDateValue() = birthdate

    fun age(clock: Clock) =
        birthdate.until(LocalDate.now(clock), ChronoUnit.YEARS).toInt()

    companion object {

        private const val MAX_AGE = 120
        private const val MIN_AGE = 14

        fun from(birthdate: LocalDate, clock: Clock): Either<BirthdateCreationError, BirthDate> {
            val today = LocalDate.now(clock)
            val age = birthdate.until(today, ChronoUnit.YEARS)

            if (age < MIN_AGE) {
                return BirthdateCreationError.TooYoung.left()
            }

            if (age > MAX_AGE) {
                return BirthdateCreationError.TooOld.left()
            }

            return BirthDate(birthdate).right()
        }
    }
}

sealed class BirthdateCreationError {
    object TooYoung : BirthdateCreationError()
    object TooOld : BirthdateCreationError()
}