package com.lgn.presentation.ui.utils

data class RegistrationFormState(
    val userCode: String = "",
    val userCodeError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)
