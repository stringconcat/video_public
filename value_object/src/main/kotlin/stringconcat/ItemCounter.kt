package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.math.BigDecimal

data class ItemCounter internal constructor(private val itemCounter: BigDecimal) {

    fun bigDecimalValue() = itemCounter

    companion object {
        fun zero() = ItemCounter(BigDecimal.ZERO)

        fun from(itemCount: ItemCount) = ItemCounter(itemCount.bigDecimalValue())

        fun from(itemCounter: BigDecimal): Either<InputValueIsNegative, ItemCounter> {
            return if (itemCounter < BigDecimal.ZERO) {
                InputValueIsNegative.left()
            } else {
                ItemCounter(itemCounter).right()
            }
        }
    }

    operator fun plus(itemCount: ItemCount): ItemCounter {
        val newValue = itemCounter.add(itemCount.bigDecimalValue())
        return ItemCounter(newValue)
    }

    operator fun plus(other: ItemCounter): ItemCounter {
        val newValue = itemCounter.add(other.bigDecimalValue())
        return ItemCounter(newValue)
    }
}

object InputValueIsNegative