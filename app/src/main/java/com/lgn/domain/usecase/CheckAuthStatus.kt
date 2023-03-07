package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository

class CheckAuthStatus(private val repository: Repository) {
    fun execute(appContext: Context): Boolean = repository.isUserLoggedIn(context = appContext)
}