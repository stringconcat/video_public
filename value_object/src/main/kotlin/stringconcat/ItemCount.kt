package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.math.BigDecimal

data class ItemCount internal constructor(private val itemCount: BigDecimal) : ValueObject {

    fun bigDecimalValue() = itemCount

    operator fun compareTo(other: ItemCount) = itemCount.compareTo(other.itemCount)

    operator fun plus(other: ItemCount) =
        ItemCount(itemCount + other.itemCount)

    operator fun minus(other: ItemCount): Either<ResultNotPositive, ItemCount> {
        val result = itemCount - other.itemCount
        return if (result >= MIN_VALUE) {
            ItemCount(result).right()
        } else {
            ResultNotPositive.left()
        }
    }

    companion object {

        const val SCALE = 1
        val MIN_VALUE = BigDecimal("0.1")

        fun from(itemCount: BigDecimal): Either<ItemCountCreationError, ItemCount> {
            return when {
                itemCount.scale() > SCALE -> ItemCountCreationError.ScaleTooLong.left()
                itemCount < MIN_VALUE -> ItemCountCreationError.ValueTooLow.left()
                else -> ItemCount(itemCount.setScale(SCALE)).right()
            }
        }

        fun min() = ItemCount(MIN_VALUE)
    }
}

sealed class ItemCountCreationError {
    object ScaleTooLong : ItemCountCreationError()
    object ValueTooLow : ItemCountCreationError()
}

object ResultNotPositive