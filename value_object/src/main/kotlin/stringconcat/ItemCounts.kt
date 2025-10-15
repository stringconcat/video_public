package stringconcat

import arrow.core.NonEmptyList
import arrow.core.getOrElse

data class ItemCounts(private val counts: NonEmptyList<ItemCount>) {
    fun average(): ItemCount {
        if (counts.size == 1) {
            return counts.first()
        } else {
            val sumRes = counts.reduce { acc, ic ->
                acc + ic
            }

            val count = Count.from(counts.size)
                .getOrElse { error("Cannot create Count from ${counts.size}") }
            return sumRes.div(count)
        }
    }
}
