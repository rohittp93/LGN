package com.lgn.presentation.dashboard.myteam.studentprofilemetrics


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.*
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
    val filterCleared = mutableStateOf<Boolean>(true)


    private val _metricsListState = mutableStateListOf<Metrics>()
    val metricsListState: List<Metrics> = _metricsListState

    private val _filteredMetricsListState = mutableStateListOf<Metrics>()
    val filteredMetricsListState: List<Metrics> = _filteredMetricsListState

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


    fun updateList(updatedList : List<Metrics>) {
        _metricsListState.clear()
        _metricsListState.addAll(updatedList)
    }

    fun updateFilterList(updatedList : List<Metrics>) {
        _filteredMetricsListState.clear()
        _filteredMetricsListState.addAll(updatedList)
    }

    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun fetchStudentProfileMetrics(context: Context, userId: String) {
        viewModelScope.launch {
            useCase.fetchStudentProfileMetrics(context, userId).collect { response ->
                _usersState.value = response
            }
        }
    }
}