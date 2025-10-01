package stringconcat

import java.time.Clock
import java.util.UUID

data class UserId(val id: UUID = UUID.randomUUID())

data class PaymentId(val id: Long) {
    companion object {
        fun generate(instanceId: Byte, clock: Clock): PaymentId {
            TODO()
        }
    }
}