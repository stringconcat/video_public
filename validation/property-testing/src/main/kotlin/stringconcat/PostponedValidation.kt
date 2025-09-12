package stringconcat

import java.time.OffsetDateTime
import java.util.UUID
import arrow.core.Either
import arrow.core.left
import arrow.core.right

sealed class Package(
    val id: UUID,
    val receivedAt: OffsetDateTime,
    val rawContent: ByteArray
)

class IncomingPackage(
    id: UUID,
    receivedAt: OffsetDateTime,
    rawContent: ByteArray
) : Package(id, receivedAt, rawContent) {
    fun validate(): Package =
        if (valid(rawContent)) {
            val xml = parseXml()
            val orderNumber = parseOrderNumber(xml)
            val address = parseAddress(xml)

            if (orderNumber is OrderNumber.ValidOrder &&
                address is Address.ValidAddress
            ) {
                ValidPackage(
                    id = id,
                    receivedAt = receivedAt,
                    rawContent = rawContent,
                    xml = xml,
                    address = address,
                    orderNumber = orderNumber
                )
            } else {
                ManualPackage(
                    id = id,
                    receivedAt = receivedAt,
                    rawContent = rawContent,
                    xml = xml,
                    address = address,
                    orderNumber = orderNumber
                )
            }

        } else {
            InvalidPackage(
                id = id,
                receivedAt = receivedAt,
                rawContent = rawContent
            )
        }


    private fun valid(rawContent: ByteArray): Boolean {
        TODO()
    }

    private fun parseXml(): String {
        TODO()
    }

    private fun parseOrderNumber(xml: String): OrderNumber {
        TODO()
    }

    private fun parseAddress(xml: String): Address {
        TODO()
    }

}


class ValidPackage(
    id: UUID,
    receivedAt: OffsetDateTime,
    rawContent: ByteArray,
    val xml: String,
    val address: Address.ValidAddress,
    val orderNumber: OrderNumber.ValidOrder
) : Package(id, receivedAt, rawContent)

class ManualPackage(
    id: UUID,
    receivedAt: OffsetDateTime,
    rawContent: ByteArray,
    val xml: String,
    val address: Address,
    val orderNumber: OrderNumber
) : Package(id, receivedAt, rawContent) {
    fun markAsValid(): ValidPackage {
        val address = when (address) {
            is Address.InvalidAddress -> address.markAsValid()
            is Address.ValidAddress -> address
        }
        val number = when (orderNumber) {
            is OrderNumber.InvalidOrder -> orderNumber.markAsValid()
            is OrderNumber.ValidOrder -> orderNumber
        }
        return ValidPackage(id, receivedAt, rawContent, xml, address, number)
    }
}

class InvalidPackage(
    id: UUID,
    receivedAt: OffsetDateTime,
    rawContent: ByteArray
) : Package(id, receivedAt, rawContent)
