package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope

class LogoutUser(private val repository: Repository) {
    operator fun invoke(context: Context) = repository.logoutUser(context)
}