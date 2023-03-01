package com.lgn.presentation.splash

import androidx.lifecycle.ViewModel
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: UseCases) : ViewModel() {
    fun isUserLoggedIn() = useCase.checkAuthStatus.execute()
}