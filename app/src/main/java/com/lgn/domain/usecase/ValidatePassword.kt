package com.lgn.domain.usecase

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter Password"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}