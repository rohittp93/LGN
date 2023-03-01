package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope

class LoginUser(private val repository: Repository) {
    suspend operator fun invoke(
        userCode: String,
        password: String,
        context: Context,
        scope: CoroutineScope
    ) =
        repository.loginWithUserCodeAndPassword(userCode, password, context, scope)
}