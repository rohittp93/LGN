package com.lgn.data

import android.content.Context
import android.net.Uri
import com.lgn.domain.model.AuthResult
import com.lgn.domain.model.Response
import com.lgn.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImpl @Inject constructor() : Repository {

    override suspend fun loginWithUserCodeAndPassword(
        userCode: String,
        password: String,
        context: Context,
        scope: CoroutineScope
    ): Flow<Response<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun logoutUser(context: Context): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

}