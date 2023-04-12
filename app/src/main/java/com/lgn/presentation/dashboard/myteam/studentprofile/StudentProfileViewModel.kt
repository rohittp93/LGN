package com.lgn.presentation.dashboard.myteam.studentprofile


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.UpdateStudentResponse
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentData
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

    val studentStatusState = mutableStateOf<Response<UpdateStudentResponse>>(Response.Idle)

    private val _showDialog = MutableStateFlow(false)

    private val _showProgress = mutableStateOf<Boolean>(false)
    val showProgress: State<Boolean> = _showProgress

    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()


    private val _showGraduateDialog = MutableStateFlow(false)
    val showGraduateDialog: StateFlow<Boolean> = _showGraduateDialog.asStateFlow()


    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun showProgress(show: Boolean) {
        _showProgress.value = show
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun onOpenGraduateDialogClicked() {
        _showGraduateDialog.value = true
    }

    fun onGraduateDialogConfirm() {
        _showGraduateDialog.value = false
    }

    fun onGraduateDialogDismiss() {
        _showGraduateDialog.value = false
    }

    var rolseState by mutableStateOf("Associate")
    var loadedFirstTime by mutableStateOf(true)

    var exiting by mutableStateOf(false)

    init {
    }

    fun updateRole(role: String) {
        rolseState = role
    }


    fun changeToGraduate(context: Context, user: StudentData) {
        viewModelScope.launch {
            useCase.changeToGraduate(context, user).collect { response ->
                _changeToGraduateState.value = response
            }
        }
    }

    fun updateStatus(context: Context, id: String, status: Int, role: String, user: StudentData) {
        Log.d("RTAG", "updateStatus in vm called")
        viewModelScope.launch {
            useCase.updateStudentStatus(context, user, status).collect { response ->
                studentStatusState.value = response
            }
        }
    }
}