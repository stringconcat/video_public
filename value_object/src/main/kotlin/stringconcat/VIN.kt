package stringconcat

import arrow.core.Either

// https://en.wikipedia.org/wiki/Vehicle_identification_number

sealed class VIN(private val vin: String) {
    fun stringValue() = vin

    fun year(): Int = TODO()

    fun country(): String = TODO()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VIN

        return vin == other.vin
    }

    override fun hashCode(): Int {
        return vin.hashCode()
    }


    //....
}

class NorthAmericanVIN(vin: String) : VIN(vin) {
    fun checkSum(): Int = TODO()
}

class EuropeanVIN(vin: String) : VIN(vin)

class VINFactory {
    fun from(vin: String): Either<VINCreationError, VIN> {
        // проверить формат, производителя и тд
        TODO()
    }
}

sealed interface VINCreationError {
    object InvalidCheckSum : VINCreationError
    object InvalidFormat : VINCreationError
    object UnknownManufacturer : VINCreationError
    //.....
}