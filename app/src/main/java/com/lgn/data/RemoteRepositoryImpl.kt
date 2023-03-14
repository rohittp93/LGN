package com.lgn.data

import android.content.Context
import com.lgn.core.Constants.KEY_IS_LOGGED
import com.lgn.data.ApiService.Companion.apiService
import com.lgn.domain.model.*
import com.lgn.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

// TODO: REMOVE ALL MOCK API RESPONSES AND REQUESTS
@Singleton
class RemoteRepositoryImpl @Inject constructor() : Repository {

    override suspend fun loginWithUserCodeAndPassword(
        userCode: String,
        password: String,
        context: Context,
        scope: CoroutineScope
    ) = flow {
        emit(Response.Loading)
        //val apiService = ApiService.getInstance()
        try {
            //val authResult = apiService.login(userCode, password)

            val mockAuthResult = AuthResult(
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCM",
                User(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    userAddress = "example address",
                    userBlock = "example block",
                    userDistrict = "example district",
                    userPin = "102103",
                    userState = "example state",
                    userAadhar = "1234567890",
                    role = "Trainer",
                    status = 1
                )
            )
            delay(3000L)
            val dataStore = LocalDataStore(context)
            dataStore.saveBooleanValue(KEY_IS_LOGGED, true)
            emit(Response.Success(mockAuthResult))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun fetchTeam(context: Context): Flow<Response<TeamData>> = flow {
        emit(Response.Loading)
        //val apiService = ApiService.getInstance()
        try {
            //val authResult = apiService.fetchTeam(userCode, password)

            val studentList = mutableListOf<StudentData>()
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Trainer",
                    batch = "null"
                )
            )
            val mockTeamResult = TeamData(
                id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                userName = "Nikita",
                userEmail = "example@gmail.com",
                userPhone = "123456789",
                userAddress = "example address",
                userBlock = "example block",
                userDistrict = "example district",
                userPin = "102103",
                userState = "example state",
                userAadhar = "1234567890",
                role = "Trainer",
                status = 1,
                associate = studentList
            )
            delay(2000L)
            emit(Response.Success(mockTeamResult))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun fetchStudents(context: Context, monthYear: String): Flow<Response<List<Users>>> =
        flow {
            emit(Response.Loading)
            //val apiService = ApiService.getInstance()
            try {
                //val userResult = apiService?.fetchStudents(monthYear)
                //val students = userResult?.users

                val studentList = mutableListOf<Users>()
                studentList.add(
                    Users(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userName = "Nikita",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
                studentList.add(
                    Users(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )

                delay(2000L)
               /* students?.let {
                    emit(Response.Success(students))
                }*/

                emit(Response.Success(studentList))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }


    override fun logoutUser(context: Context): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(context: Context): Boolean {
        val dataStore = LocalDataStore(context)
        return runBlocking { dataStore.getBooleanValue(KEY_IS_LOGGED).first() } ?: false
    }
}