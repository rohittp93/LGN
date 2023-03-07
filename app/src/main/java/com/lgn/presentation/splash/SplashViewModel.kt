package com.lgn.presentation.splash

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lgn.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: UseCases, application: Application) : AndroidViewModel(application) {
    var isUserLoggedIn = false

    init {
        isUserLoggedIn = useCase.checkAuthStatus.execute(application.applicationContext)
    }
}