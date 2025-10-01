package stringconcat

import arrow.core.Either
import arrow.core.left
import arrow.core.right

data class ItemWeightRange internal constructor(
    val from: ItemWeight,
    val to: ItemWeight,
) : ValueObject {

    companion object {
        fun from(from: ItemWeight, to: ItemWeight): Either<InvalidItemWeightRange, ItemWeightRange> {
            return if (from > to) {
                InvalidItemWeightRange.left()
            } else {
                ItemWeightRange(from, to).right()
            }
        }
    }
}

object InvalidItemWeightRange