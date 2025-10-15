package stringconcat.signature

import arrow.core.NonEmptyList
import stringconcat.ItemCount
import stringconcat.Role

fun Set<Role>.checkHasAdminRoles(): Boolean {
    TODO("Is it admin?")
}


fun List<ItemCount>.average(): ItemCount {
    check(isNotEmpty()) // может выпасть ошибка или исключение
    TODO("Calculate average")
}

fun NonEmptyList<ItemCount>.average(): ItemCount {
    TODO("Calculate average")
}