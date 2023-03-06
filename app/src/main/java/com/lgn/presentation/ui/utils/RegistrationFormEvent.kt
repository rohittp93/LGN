package com.lgn.presentation.ui.utils

sealed class RegistrationFormEvent {
    data class UserCodeChanged(val userCode: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}
