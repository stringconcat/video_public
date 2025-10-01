package stringconcat

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID


fun ItemCount.Companion.restore(count: BigDecimal): ItemCount {
    val res = count.abs()
    return ItemCount.fromOrError(res)
}

fun ItemCount.Companion.restoreWithVersion(count: BigDecimal, version: Int): ItemCount {
    val res = when (version) {
        1 -> count.abs()
        2 -> count.setScale(1)
        else -> count
    }
    return ItemCount.fromOrError(res)
}
// -----

data class CustomerId(val id: UUID)

class Customer(
    val id: CustomerId,
    val created: OffsetDateTime,
    val emails: Set<Email>
)

// customers(id, created)
// customer_email(customer_id, email) -- customer_id + email = PK

// -----
data class OrderId(val id: UUID)
data class ProductId(val id: UUID)
data class OrderItemId(val id: UUID)

data class OrderItem(val id: OrderItemId, val productId: ProductId, val count: Count)

class Order(
    val id: OrderId,
    val customer: CustomerId,
    val created: OffsetDateTime,
    val item: List<OrderItem>
)
// order(id, customer_id, created)
// order_item(id, order_id, count, product_id)

data class OrderContent(val productId: ProductId, val count: Count)
data class OrderItemWithContent(val id: OrderItemId, val content: OrderContent)