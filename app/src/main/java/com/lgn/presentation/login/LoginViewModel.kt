package com.lgn.presentation.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.AuthResult
import com.lgn.domain.model.Response
import com.lgn.domain.usecase.UseCases
import com.lgn.presentation.ui.utils.RegistrationFormEvent
import com.lgn.presentation.ui.utils.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    private val _registerState = MutableLiveData<Response<AuthResult>>(Response.Idle)
    val registerState: LiveData<Response<AuthResult>> get() = _registerState

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.UserCodeChanged -> {
                state = state.copy(userCode = event.userCode)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }


    fun setStateIdle() {
        _registerState.postValue(Response.Idle)
    }

    private fun submitData() {
        val userCodeResult = useCase.validateUserCode.execute(state.userCode)
        val passwordResult = useCase.validatePassword.execute(state.password)

        val hasError = listOf(
            userCodeResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                userCodeError = userCodeResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        state = state.copy(
            userCodeError = null,
            passwordError =null
        )
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


    fun loginAdmin(context: Context, scope: CoroutineScope) {
        viewModelScope.launch {
            useCase.loginUser.invoke(
                state.userCode,
                state.password,
                context,
                scope
            ).collect() { response ->
                response.let {
                    _registerState.postValue(response)
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}