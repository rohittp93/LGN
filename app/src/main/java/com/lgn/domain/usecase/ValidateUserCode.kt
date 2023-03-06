package com.lgn.domain.usecase

import android.util.Patterns

class ValidateUserCode {

    fun execute(userCode: String): ValidationResult {
        if(userCode.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The User Code can't be blank"
            )
        }
        /*if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }*/
        return ValidationResult(
            successful = true
        )
    }
}