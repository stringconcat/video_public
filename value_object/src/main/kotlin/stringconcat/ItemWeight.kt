package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.math.BigDecimal
import java.math.RoundingMode

data class ItemWeight internal constructor(private val itemWeightGram: Int) : ValueObject {

    fun intValue() = itemWeightGram

    operator fun compareTo(other: ItemWeight) = itemWeightGram.compareTo(other.itemWeightGram)

    operator fun plus(other: ItemWeight) =
        ItemWeight(itemWeightGram + other.itemWeightGram)

    operator fun minus(other: ItemWeight): Either<ItemSubtractionResultNotPositive, ItemWeight> {
        val result = itemWeightGram - other.itemWeightGram
        return if (result >= MIN_VALUE) {
            ItemWeight(result).right()
        } else {
            ItemSubtractionResultNotPositive.left()
        }
    }

    operator fun times(itemCount: ItemCount): ItemWeight {
        val result = itemCount.bigDecimalValue()
            .multiply(BigDecimal(intValue()))

        val toInt = result.setScale(0, RoundingMode.HALF_UP).intValueExact()
        return if (toInt == 0) {
            ItemWeight(MIN_VALUE)
        } else {
            ItemWeight(toInt)
        }
    }

    operator fun div(weight: ItemWeight): ItemCount {
        val result = this.intValue().toBigDecimal() / weight.intValue().toBigDecimal()
        return ItemCount.from(result.setScale(ItemCount.SCALE, RoundingMode.HALF_UP))
            .fold(
                ifLeft = {
                    when (it) {
                        ItemCountCreationError.ScaleTooLong -> error("Unreachable result")
                        ItemCountCreationError.ValueTooLow -> ItemCount.min()
                    }
                },
                ifRight = { it }
            )
    }

    companion object {

        const val MIN_VALUE = 1

        fun from(itemWeightGram: Int): Either<ItemValueTooLow, ItemWeight> {
            return when {
                itemWeightGram < MIN_VALUE -> ItemValueTooLow.left()
                else -> ItemWeight(itemWeightGram).right()
            }
        }
    }
}

object ItemValueTooLow

object ItemSubtractionResultNotPositive