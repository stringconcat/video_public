package stringconcat

import arrow.core.left
import arrow.core.right
import java.math.BigDecimal

data class Coordinates(val lat: Latitude, val lon: Longitude)

data class Latitude private constructor(private val lat: BigDecimal) {
    fun bigDecimalValue() = lat

    companion object {
        private const val MAX_ABS_VALUE = 90
        fun from(lat: BigDecimal) =
            when {
                lat > BigDecimal(MAX_ABS_VALUE) -> CoordinateCreationError.TooBig.left()
                lat < BigDecimal(-MAX_ABS_VALUE) -> CoordinateCreationError.TooLow.left()
                else -> Latitude(lat).right()
            }
    }
}

data class Longitude private constructor(private val lon: BigDecimal) {
    fun bigDecimalValue() = lon

    companion object {
        private const val MAX_ABS_VALUE = 180
        fun from(lon: BigDecimal) =
            when {
                lon > BigDecimal(MAX_ABS_VALUE) -> CoordinateCreationError.TooBig.left()
                lon < BigDecimal(-MAX_ABS_VALUE) -> CoordinateCreationError.TooLow.left()
                else -> Longitude(lon).right()
            }
    }
}

sealed interface CoordinateCreationError { // можно разделить на 2 класса
    data object TooBig : CoordinateCreationError
    data object TooLow : CoordinateCreationError
}