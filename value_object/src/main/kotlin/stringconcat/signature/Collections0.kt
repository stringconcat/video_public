package stringconcat.signature

import stringconcat.ItemCount
import stringconcat.Role

fun checkHasAdminRoles(roles: Set<Role>): Boolean {
    if (roles.isEmpty()) {
        return false
    }
    TODO("Is it admin?")
}


fun average(counts: List<ItemCount>): ItemCount {
    check(counts.isNotEmpty()){
        "Count's must not be empty"
    }
    TODO("Calculate average")
}