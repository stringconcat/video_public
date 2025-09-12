package stringconcat.validation

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/card")
@Validated
class PayController {

    @PostMapping
    fun createUser(@Valid @RequestBody request: PayRequest): ResponseEntity<String> {
        // If the request body is valid, this method will be executed
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction completed successfully: ${request.card}")
    }
}

data class PayRequest(
    @field:MirCard(message = "Invalid card")
    val card: String
)
