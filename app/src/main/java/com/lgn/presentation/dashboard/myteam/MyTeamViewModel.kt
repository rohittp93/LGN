package com.lgn.presentation.dashboard.myteam


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentData
import com.lgn.domain.model.TeamData
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTeamViewModel @Inject constructor(private val useCase: UseCases, application: Application) :
    AndroidViewModel(application) {
    private val _teamState = mutableStateOf<Response<TeamData>>(Response.Loading)
    val teamState: State<Response<TeamData>> = _teamState
    var showFilterList = mutableStateOf(true)
    var checkRefreshState = mutableStateOf(false)
    var userTypeFilter = mutableStateOf("Show All")
    var statusFilter = mutableStateOf("Both")
    var yearFilterSelected = mutableStateOf("")

    private val _teamListState = mutableStateListOf<StudentData>()
    val teamListState: List<StudentData> = _teamListState

    private val _filteredTeamListState = mutableStateListOf<StudentData>()
    val filteredTeamListState: List<StudentData> = _filteredTeamListState

    private val _isStudentAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isStudentAddedState: State<Response<Void?>> = _isStudentAddedState

    /*private val _isEventDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isEventDeletedState: State<Response<Void?>> = _isEventDeletedState*/

    init {
        fetchTeam(application.applicationContext)
    }


    fun updateList(updatedList : List<StudentData>) {
        _teamListState.clear()
        _teamListState.addAll(updatedList)
    }

    fun updateFilterList(updatedList : List<StudentData>) {
        _filteredTeamListState.clear()
        _filteredTeamListState.addAll(updatedList)
    }

    fun fetchTeam(context: Context) {
        showFilterList(false)
        viewModelScope.launch {
            useCase.fetchTeam(context).collect { response ->
                _teamState.value = response
            }
        }
    }

    fun showFilterList(show: Boolean) {
        showFilterList.value = show
    }
}