package com.lgn.presentation.dashboard.metrics.studentmetricsdetail


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
import com.lgn.domain.usecase.UpdateStudentMetrics
import com.lgn.domain.usecase.UseCases
import com.lgn.presentation.ui.utils.StudentMetricState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMetricsViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _metricsUpdateState =
        mutableStateOf<Response<StudentMerticsResponse>>(Response.Idle)
    val metricsUpdateState: State<Response<StudentMerticsResponse>> = _metricsUpdateState
    var initialLaunchCompleted = false

    var state by mutableStateOf(StudentMetricState())


    init {
        //fetchStudentMetrics(application.applicationContext)
    }

    fun updateStudentMetrics(context: Context, metrics: StudentMerticsResponse) {
        viewModelScope.launch {
            useCase.updateStudentMetrics(context, metrics).collect { response ->
                _metricsUpdateState.value = response
            }
        }
    }

    fun updateStudentMetrics(studentMetrics: StudentMerticsResponse) {
        state = state.copy(
            id = studentMetrics.id,
            userId = studentMetrics.userId,
            monthyear = studentMetrics.monthyear,
            ev = studentMetrics.ev,
            de = studentMetrics.de,
            jb = studentMetrics.jb,
            aa = studentMetrics.aa,
            p = studentMetrics.p,
            e = studentMetrics.e,
            a = studentMetrics.a,
            c = studentMetrics.c,
            ed = studentMetrics.ed,
            isDeleted = studentMetrics.isDeleted
        )

        initialLaunchCompleted = true
    }

    fun valueChanged(element: String, newText: String) {
        when(element) {
             "ev"-> {
                 state = state.copy(ev = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "de"-> {
                state = state.copy(de = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "jb"-> {
                state = state.copy(jb = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "aa"-> {
                state = state.copy(aa= if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "p"-> {
                state = state.copy(p = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "e"-> {
                state = state.copy(e = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "a"-> {
                state = state.copy(a = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "c"-> {
                state = state.copy(c = if (newText.isNotEmpty()) newText.toInt() else -1)
            }
            "ed"-> {
                state = state.copy(ed = if (newText.isNotEmpty()) newText.toInt() else -1)
            }

        }
    }
}