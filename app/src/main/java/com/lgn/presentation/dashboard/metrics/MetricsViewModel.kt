package com.lgn.presentation.dashboard.metrics


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.TeamData
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(private val useCase: UseCases, application: Application) :
    AndroidViewModel(application) {
    private val _teamState = mutableStateOf<Response<TeamData>>(Response.Loading)

    private val _isStudentAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isStudentAddedState: State<Response<Void?>> = _isStudentAddedState

    /*private val _isEventDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isEventDeletedState: State<Response<Void?>> = _isEventDeletedState*/

    init {
        fetchTeam(application.applicationContext)
    }

    private fun fetchTeam(context: Context) {
        viewModelScope.launch {
            useCase.fetchTeam(context).collect { response ->
                _teamState.value = response
            }
        }
    }
}