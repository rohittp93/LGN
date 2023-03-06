package com.lgn.presentation.dashboard


import androidx.lifecycle.ViewModel
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {


}