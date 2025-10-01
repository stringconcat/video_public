package stringconcat

import arrow.core.Either
import arrow.core.getOrElse
import java.math.BigDecimal

fun ItemCount.Companion.fromAnyScale(value: BigDecimal): Either<ItemCountCreationFromAnyScaleError, ItemCount> {
    val destValue = value.setScale(SCALE)
    return from(destValue).mapLeft {
        when(it){
            ItemCountCreationError.ScaleTooLong -> error("Can't be here")
            ItemCountCreationError.ValueTooLow -> ItemCountCreationFromAnyScaleError.ValueTooLow
        }
    }
}

fun ItemCount.Companion.fromOrError(value: BigDecimal) =
    from(value).getOrElse { error("Cannot create ItemCount from $value") }

sealed class ItemCountCreationFromAnyScaleError {
    object ValueTooLow : ItemCountCreationFromAnyScaleError()
}