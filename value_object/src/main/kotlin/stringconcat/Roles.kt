package stringconcat

import arrow.core.NonEmptySet
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.toNonEmptySetOrNull

data class Roles(private val roles: NonEmptySet<Role>) {
    fun isAdmin() =
        roles.contains(Role.ADMIN) ||
                (roles.contains(Role.USER_EDIT)
                        && roles.contains(Role.DOCUMENT_EDIT))

    fun canViewDocuments() = roles.contains(Role.DOCUMENT_VIEW) ||
            roles.contains(Role.DOCUMENT_EDIT)

    fun addRole(role: Role): Roles {
        val newRoles = roles + role // тоже переопределены операторы
        return Roles(newRoles)
    }

    fun removeRole(role: Role) =
        either {
            ensure(roles.contains(role)) {
                RolesRemoveError.NotExists
            }
            val mutableRoles = mutableSetOf<Role>()
            mutableRoles.remove(role)

            val newRoles = mutableRoles.toNonEmptySetOrNull()
            ensure(newRoles != null) {
                RolesRemoveError.RolesIsEmpty
            }
            Roles(newRoles)
        }

    fun asSet(): Set<Role> = roles.toHashSet() // инкапсулируем внутренности

    // не забудь про сравнение!
}

sealed interface RolesRemoveError {
    object NotExists : RolesRemoveError
    object RolesIsEmpty : RolesRemoveError
}
