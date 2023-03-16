package com.lgn.presentation.dashboard.myteam.studentprofile


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.UpdateStudentResponse
import com.lgn.domain.model.Response
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _changeToGraduateState = mutableStateOf<Response<UpdateStudentResponse>>(Response.Idle)
    val changeToGraduateState: State<Response<UpdateStudentResponse>> = _changeToGraduateState

    private val _studentStatusState = mutableStateOf<Response<UpdateStudentResponse>>(Response.Idle)
    val studentStatusState: State<Response<UpdateStudentResponse>> = _studentStatusState

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    var rolseState by mutableStateOf("Associate")
    var loadedFirstTime by mutableStateOf(true)

    var exiting by mutableStateOf(false)

    init {
    }

    fun updateRole(role: String) {
        rolseState = role
    }


    fun changeToGraduate(context: Context, id: String, batch: String) {
        viewModelScope.launch {
            useCase.changeToGraduate(context, id, batch).collect { response ->
                _changeToGraduateState.value = response
            }
        }
    }

    fun updateStatus(context: Context, id: String, status: Int) {
        viewModelScope.launch {
            useCase.updateStudentStatus(context, id, status).collect { response ->
                _studentStatusState.value = response
            }
        }
    }
}