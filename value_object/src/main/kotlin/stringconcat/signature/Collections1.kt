package stringconcat.signature

import arrow.core.NonEmptyList
import arrow.core.NonEmptySet
import stringconcat.ItemCount
import stringconcat.Role

/**
 * Непустые коллекции
 * https://arrow-kt.io/learn/collections-functions/non-empty/
 */

fun checkHasAdminRoles(roles: NonEmptySet<Role>): Boolean {
    TODO("Is it admin?")
}


fun average(counts: NonEmptyList<ItemCount>): ItemCount {
    TODO("Calculate average")
}