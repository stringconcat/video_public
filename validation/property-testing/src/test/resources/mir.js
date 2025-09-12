var ValidationError;
(function (ValidationError) {
    ValidationError["InvalidLength"] = "InvalidLength";
    ValidationError["InvalidCharacters"] = "InvalidCharacters";
    ValidationError["InvalidChecksum"] = "InvalidChecksum";
    ValidationError["InvalidBin"] = "InvalidBin";
})(ValidationError || (ValidationError = {}));
function validateMirCardNumber(cardNumber) {
    if (cardNumber.length < 16 || cardNumber.length > 19) {
        return { left: ValidationError.InvalidLength };
    }
    if (!/^\d+$/.test(cardNumber)) {
        return { left: ValidationError.InvalidCharacters };
    }
    var bin = parseInt(cardNumber.substring(0, 4), 10);
    if (bin < 2200 || bin > 2204) {
        return { left: ValidationError.InvalidBin };
    }
    if (!luhnAlgorithm(cardNumber)) {
        return { left: ValidationError.InvalidChecksum };
    }
    return { right: undefined };
}
function luhnAlgorithm(cardNumber) {
    return cardNumber
        .split('')
        .reverse()
        .map(function (char, index) {
        var digit = parseInt(char, 10);
        if (index % 2 === 0) {
            return digit;
        }
        else {
            return digit < 5 ? digit * 2 : digit * 2 - 9;
        }
    })
        .reduce(function (sum, digit) { return sum + digit; }, 0) % 10 === 0;
}
