package com.lgn.presentation.dashboard.metrics.studentmetricsdetail


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.Users
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentMetricsDetailViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _usersState = mutableStateOf<Response<StudentMerticsResponse>>(Response.Loading)
    val usersState: State<Response<StudentMerticsResponse>> = _usersState

    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun fetchStudentMetrics(context: Context, monthYear: String) {
        viewModelScope.launch {
            useCase.fetchStudentMetrics(context, monthYear).collect { response ->
                _usersState.value = response
            }
        }
    }
}