package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right

data class ItemWeightCounter internal constructor(private val itemWeightGram: Int) {

    fun intValue() = itemWeightGram

    companion object {
        fun zero() = ItemWeightCounter(0)

        fun from(itemWeight: ItemWeight) = ItemWeightCounter(itemWeight.intValue())

        fun from(itemWeight: Int): Either<ItemWeightLessThanZero, ItemWeightCounter> {
            return if (itemWeight < 0) {
                ItemWeightLessThanZero.left()
            } else {
                ItemWeightCounter(itemWeight).right()
            }
        }
    }

    operator fun plus(weight: ItemWeight) =
        ItemWeightCounter(itemWeightGram + weight.intValue())

    operator fun plus(other: ItemWeightCounter) =
        ItemWeightCounter(itemWeightGram + other.intValue())
}

object ItemWeightLessThanZero