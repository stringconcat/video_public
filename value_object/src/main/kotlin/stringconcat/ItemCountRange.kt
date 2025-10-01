package stringconcat

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import java.math.BigDecimal

data class ItemCountRange internal constructor(
    val from: ItemCount,
    val to: ItemCount,
) {

    init {
        check(from <= to)
    }

    companion object {
        fun from(from: ItemCount, to: ItemCount): Either<InvalidRange, ItemCountRange> {
            return if (from > to) {
                InvalidRange.left()
            } else {
                ItemCountRange(from, to).right()
            }
        }
    }

    fun expand(ranges: Set<ItemCountRange>): ItemCountRange {
        return if (ranges.isNotEmpty()) {

            val from = ranges.sumOf { it.from.bigDecimalValue() }
            val to = ranges.sumOf { it.to.bigDecimalValue() }

            val countFrom = ItemCount
                .from(from.max(this.from.bigDecimalValue()))
                .getOrElse { error("Cannot create ItemCount") }

            val countTo = ItemCount
                .from(to.max(this.to.bigDecimalValue()))
                .getOrElse { error("Cannot create ItemCount") }

            ItemCountRange(countFrom, countTo)
        } else {
            this
        }
    }

    fun diff(ranges: Set<ItemCountRange>): Either<InvalidDiff, ItemCountRange> {
        return if (ranges.isNotEmpty()) {
            val from = ranges.sumOf { it.from.bigDecimalValue() }
            val to = ranges.sumOf { it.to.bigDecimalValue() }

            val fromDiff = this.from.bigDecimalValue() - from
            if (fromDiff <= BigDecimal.ZERO) {
                return InvalidDiff.left()
            }

            val toDiff = this.to.bigDecimalValue() - to
            if (toDiff <= BigDecimal.ZERO) {
                return InvalidDiff.left()
            }

            val countFrom = ItemCount
                .from(fromDiff)
                .getOrElse { error("Cannot create ItemCount") }

            val countTo = ItemCount
                .from(toDiff)
                .getOrElse { error("Cannot create ItemCount") }

            return if (countFrom <= countTo) {
                ItemCountRange(countFrom, countTo)
            } else {
                ItemCountRange(countTo, countFrom)
            }.right()
        } else {
            this.right()
        }
    }
}

object InvalidRange

object InvalidDiff // if diff <= 0