package com.lgn.presentation.dashboard.metrics.allmetrics


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentData
import com.lgn.domain.model.Users
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMetricsViewModel @Inject constructor(private val useCase: UseCases, application: Application) : AndroidViewModel(application) {
    private val _usersState = mutableStateOf<Response<List<Users>>>(Response.Loading)
    val usersState: State<Response<List<Users>>> = _usersState

    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun fetchStudentMetrics(context: Context, monthYear: String) {
        viewModelScope.launch {
            useCase.fetchStudents(context, monthYear).collect { response ->
                _usersState.value = response
            }
        }
    }
}