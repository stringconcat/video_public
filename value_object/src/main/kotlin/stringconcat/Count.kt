package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right

data class Count(private val value: Int) : ValueObject {

    init {
        require(value >= 0)
    }

    companion object {
        fun from(count: Int): Either<NegativeValueError, Count> {
            return if (count < 0) {
                NegativeValueError.left()
            } else {
                Count(count).right()
            }
        }

        fun one() = Count(1)

        fun zero() = Count(0)
    }

    fun increment(): Either<MaxValueReachedError, Count> {
        val res = value + 1
        return if (res > value) {
            Count(res).right()
        } else {
            MaxValueReachedError.left()
        }
    }

    fun decrement(): Either<MinValueReachedError, Count> {
        val res = value - 1
        return if (res >= 0) {
            Count(res).right()
        } else {
            MinValueReachedError.left()
        }
    }

    fun isMin() = value == 0

    fun isMax() = value == Int.MAX_VALUE

    fun toIntValue() = value
}

object NegativeValueError

object MaxValueReachedError

object MinValueReachedError