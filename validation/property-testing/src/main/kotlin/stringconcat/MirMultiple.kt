import arrow.core.Either
import arrow.core.left
import arrow.core.right
import stringconcat.ValidationError
import stringconcat.luhnAlgorithm

fun validateMirCardNumberWithMultipleErrors(cardNumber: String):
        Either<List<ValidationError>, Unit> { //  можно заменить на NonEmptyList

    val errorResult = mutableListOf<ValidationError>()

    if (cardNumber.length !in 16..19) {
        errorResult.add(ValidationError.InvalidLength)
    }

    if (!cardNumber.all { it.isDigit() }) {
        errorResult.add(ValidationError.InvalidCharacters)
    }

    val bin = cardNumber.take(4).toInt()
    if (bin !in 2200..2204) {
        errorResult.add(ValidationError.InvalidBin)
    }

    if (!cardNumber.luhnAlgorithm()) {
        errorResult.add(ValidationError.InvalidChecksum)
    }

    return if (errorResult.isNotEmpty()) {
        errorResult.left()
    } else {
        Unit.right()
    }
}