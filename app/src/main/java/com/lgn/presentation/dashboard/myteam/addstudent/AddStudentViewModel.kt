package com.lgn.presentation.dashboard.myteam.addstudent


import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.UpdateStudentResponse
import com.lgn.domain.usecase.UpdateStudentMetrics
import com.lgn.domain.usecase.UseCases
import com.lgn.presentation.ui.utils.AddStudentState
import com.lgn.presentation.ui.utils.StudentMetricState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _studentAddedeState =
        mutableStateOf<Response<UpdateStudentResponse>>(Response.Idle)
    val studentAddedeState: State<Response<UpdateStudentResponse>> = _studentAddedeState
    var initialLaunchCompleted = false

    var state by mutableStateOf(AddStudentState())


    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun addStudent(context: Context, student: UpdateStudentResponse) {
        viewModelScope.launch {
            useCase.addStudent(context, student).collect { response ->
                _studentAddedeState.value = response
            }
        }
    }

    fun valueChanged(element: String, newText: String) {
        when(element) {
             "name"-> {
                 state = state.copy(name = newText)
            }
            "email"-> {
                state = state.copy(email = newText)
            }
            "phone"-> {
                state = state.copy(phone = newText)
            }
        }
    }
}