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
class UpdateMetricsViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _metricsUpdateState = mutableStateOf<Response<StudentMerticsResponse>>(Response.Idle)
    val metricsUpdateState: State<Response<StudentMerticsResponse>> = _metricsUpdateState

    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun updateStudentMetrics(context: Context, metrics : StudentMerticsResponse) {
        viewModelScope.launch {
            useCase.updateStudentMetrics(context, metrics).collect { response ->
                _metricsUpdateState.value = response
            }
        }
    }
}