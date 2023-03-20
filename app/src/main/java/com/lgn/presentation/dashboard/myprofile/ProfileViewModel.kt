package com.lgn.presentation.dashboard.myprofile


import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.UserProfile
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: UseCases,
    application: Application
) : AndroidViewModel(application) {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()
    private val _logoutState = MutableLiveData<Response<Boolean?>>(Response.Idle)
    val logoutState: LiveData<Response<Boolean?>> get() = _logoutState
    var userProfile =
        mutableStateOf(UserProfile())

    init {
        fetchUserProfileData(application.applicationContext)
    }

    private fun fetchUserProfileData(applicationContext: Context) {
        viewModelScope.launch {
            userProfile.value = useCase.getUserProfileDetails(applicationContext)
        }
    }

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    //fun getAdminDetails(context: Context) = useCase.getAdminDetails.execute(context)

    fun logoutUser(current: Context) {
        viewModelScope.launch {
            useCase.logoutUser(current).collect() { response ->
                response.let {
                    _logoutState.postValue(response)
                }
            }
        }

    }
}