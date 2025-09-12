enum ValidationError {
    InvalidLength = "InvalidLength",
    InvalidCharacters = "InvalidCharacters",
    InvalidChecksum = "InvalidChecksum",
    InvalidBin = "InvalidBin",
}

type Either<L, R> = { left: L } | { right: R };

function validateMirCardNumber(cardNumber: string): Either<ValidationError, void> {
    if (cardNumber.length < 16 || cardNumber.length > 19) {
        return { left: ValidationError.InvalidLength };
    }

    if (!/^\d+$/.test(cardNumber)) {
        return { left: ValidationError.InvalidCharacters };
    }

    const bin = parseInt(cardNumber.substring(0, 4), 10);
    if (bin < 2200 || bin > 2204) {
        return { left: ValidationError.InvalidBin };
    }

    if (!luhnAlgorithm(cardNumber)) {
        return { left: ValidationError.InvalidChecksum };
    }

    return { right: undefined };
}

function luhnAlgorithm(cardNumber: string): boolean {
    return cardNumber
        .split('')
        .reverse()
        .map((char, index) => {
            const digit = parseInt(char, 10);
            if (index % 2 === 0) {
                return digit;
            } else {
                return digit < 5 ? digit * 2 : digit * 2 - 9;
            }
        })
        .reduce((sum, digit) => sum + digit, 0) % 10 === 0;
}