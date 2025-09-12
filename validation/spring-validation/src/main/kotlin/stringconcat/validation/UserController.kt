package stringconcat.validation

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Validated
class UserController {

    @PostMapping
    fun createUser(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<String> {
        // If the request body is valid, this method will be executed
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully: ${userRequest.name}")
    }

//    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage ?: "Validation error"
        }
        return ResponseEntity.badRequest().body(errors)
    }
}

data class UserRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:Email(message = "Email should be valid")
    val email: String,

    @field:Min(value = 18, message = "Age must be at least 18")
    @field:Max(value = 100, message = "Age must be at most 100")
    val age: Int
)

