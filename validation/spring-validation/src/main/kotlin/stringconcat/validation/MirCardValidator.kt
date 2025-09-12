package stringconcat.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import stringconcat.validateMirCardNumber


@Component
class MirCardValidator : ConstraintValidator<MirCard, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return false
        }
        return validateMirCardNumber(value).isRight()
    }
}