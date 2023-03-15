package com.lgn.presentation.dashboard.myteam.studentprofilemetrics


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.StudentProfileMerticsResponse
import com.lgn.domain.model.Users
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileMetricsViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _usersState = mutableStateOf<Response<StudentProfileMerticsResponse>>(Response.Loading)
    val usersState: State<Response<StudentProfileMerticsResponse>> = _usersState


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

    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun fetchStudentProfileMetrics(context: Context, userId: String, year: String) {
        viewModelScope.launch {
            useCase.fetchStudentProfileMetrics(context, userId, year).collect { response ->
                _usersState.value = response
            }
        }
    }
}