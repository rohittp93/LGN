package com.lgn.data

import android.content.Context
import com.lgn.core.Constants.KEY_IS_LOGGED
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

    override fun updateStudentMetrics(
        context: Context,
        studentMerticsResponse: StudentMerticsResponse
    ) = flow {
        emit(Response.Loading)
        //val apiService = ApiService.getInstance()

        try {
/*
            if (studentMerticsResponse.id != null) {
                // Update Metric
                val authResult = apiService.updateStudentMetrics(studentMerticsResponse.id ?: "", studentMerticsResponse)
            } else {
                // Add Metric
                val authResult = apiService.addStudentMetrics(studentMerticsResponse)
            }*/

            delay(1000L)
            emit(Response.Success(studentMerticsResponse))
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
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Nikita",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "null",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userName = "Rohit",
                    userEmail = "example@gmail.com",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "null",
                    status = 0
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
            delay(1000L)
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )
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
                        id = "",
                        userName = "Rohit",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        role = "Trainer",
                        monthyear = "2020-01-01T00:00:00.000Z"
                    )
                )

                delay(1000L)
                emit(Response.Success(studentList))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

    override fun fetchStudentMetrics(
        context: Context,
        id: String
    ): Flow<Response<StudentMerticsResponse>> =
        flow {
            emit(Response.Loading)
            val apiService = ApiService.getInstance()
            try {
                //val studentMerticsResponse = apiService.fetchStudentMetrics(id)

                var studentMerticsResponse = StudentMerticsResponse(
                    id = id,
                    userId = "ahjdausidtbiandgas",
                    monthyear = "dadkjadkad",
                    ev = 1,
                    de = 2,
                    jb = 1,
                    aa = 5,
                    p = 7,
                    e = 9,
                    a = 5,
                    c = 2,
                    ed = 1,
                    isDeleted = 0
                )

                delay(1000L)
                emit(Response.Success(studentMerticsResponse))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }


    override fun changeToGraduate(
        context: Context,
        userId: String,
        batch: String
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        //val apiService = ApiService.getInstance()
        try {
            /*val changeToGraduateResponse = apiService.changeToGraduate(
                userId, ChangeToGraduateResponse(
                    roleId = "Graduate",
                    batch = batch
                )
            )*/

            val response = UpdateStudentResponse(
                roleId = "Graduate",
                batch = batch
            )
            delay(2000L)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun addStudent(
        context: Context,
        student: UpdateStudentResponse,
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        //val apiService = ApiService.getInstance()
        try {
            //val changeToGraduateResponse = apiService.addStudent(student)

            val response = UpdateStudentResponse(
                userFirstname = "Test",
                userEmail = "test@gmail.com",
                userPhone = "965214848"
            )
            delay(2000L)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun updateStudentStatus(
        context: Context,
        userId: String,
        status: Int
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {
            /*val changeToGraduateResponse = apiService.changeToGraduate(
                userId, UpdateStudentResponse(
                    Status = status
                )
            )*/

            val response = UpdateStudentResponse(
                Status = status
            )
            delay(2000L)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun fetchStudentProfileMetrics(
        context: Context,
        userId: String,
        year: String
    ): Flow<Response<StudentProfileMerticsResponse>> =
        flow {
            emit(Response.Loading)
            //val apiService = ApiService.getInstance()
            try {
                //val studentProfileMerticsResponse = apiService.fetchStudentProfileMetrics(userId, year)

                val metricsList = mutableListOf<Metrics>() as ArrayList

                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 1,
                        de = 2,
                        jb = 1,
                        aa = 5,
                        p = 7,
                        e = 9,
                        a = 5,
                        c = 2,
                        ed = 1,
                        isDeleted = 0
                    )
                )


                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 12,
                        de = 32,
                        jb = 16,
                        aa = 52,
                        p = 71,
                        e = 93,
                        a = 35,
                        c = 26,
                        ed = 19,
                        isDeleted = 0
                    )
                )
                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 1,
                        de = 2,
                        jb = 1,
                        aa = 5,
                        p = 7,
                        e = 9,
                        a = 5,
                        c = 2,
                        ed = 1,
                        isDeleted = 0
                    )
                )


                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 12,
                        de = 32,
                        jb = 16,
                        aa = 52,
                        p = 71,
                        e = 93,
                        a = 35,
                        c = 26,
                        ed = 19,
                        isDeleted = 0
                    )
                )
                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 1,
                        de = 2,
                        jb = 1,
                        aa = 5,
                        p = 7,
                        e = 9,
                        a = 5,
                        c = 2,
                        ed = 1,
                        isDeleted = 0
                    )
                )


                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 12,
                        de = 32,
                        jb = 16,
                        aa = 52,
                        p = 71,
                        e = 93,
                        a = 35,
                        c = 26,
                        ed = 19,
                        isDeleted = 0
                    )
                )
                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 1,
                        de = 2,
                        jb = 1,
                        aa = 5,
                        p = 7,
                        e = 9,
                        a = 5,
                        c = 2,
                        ed = 1,
                        isDeleted = 0
                    )
                )


                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 12,
                        de = 32,
                        jb = 16,
                        aa = 52,
                        p = 71,
                        e = 93,
                        a = 35,
                        c = 26,
                        ed = 19,
                        isDeleted = 0
                    )
                )
                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 1,
                        de = 2,
                        jb = 1,
                        aa = 5,
                        p = 7,
                        e = 9,
                        a = 5,
                        c = 2,
                        ed = 1,
                        isDeleted = 0
                    )
                )


                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "2020-01-01T00:00:00.000Z",
                        ev = 12,
                        de = 32,
                        jb = 16,
                        aa = 52,
                        p = 71,
                        e = 93,
                        a = 35,
                        c = 26,
                        ed = 19,
                        isDeleted = 0
                    )
                )

                var studentMerticsResponse = StudentProfileMerticsResponse(
                    metrics = metricsList
                )

                delay(1000L)
                emit(Response.Success(studentMerticsResponse))
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