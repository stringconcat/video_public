package stringconcat.signature

fun calculateShippingCost(weight: Kilograms, distance: Kilometers, discount: Percent): Money {
    TODO()
}

class Kilograms(val kg: Int)
class Kilometers(val km: Int)
class Percent(val percent: Int)
class Money(val amount: Int, currency: String)