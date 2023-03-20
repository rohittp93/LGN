package com.lgn.data

import android.content.Context
import android.widget.Toast
import com.lgn.core.Constants
import com.lgn.core.Constants.KEY_ACCESS_TOKEN
import com.lgn.core.Constants.KEY_IS_LOGGED
import com.lgn.core.Constants.KEY_USERNAME
import com.lgn.core.Constants.KEY_USER_AADHAR
import com.lgn.core.Constants.KEY_USER_ADDRESS
import com.lgn.core.Constants.KEY_USER_BLOCK
import com.lgn.core.Constants.KEY_USER_DISTRICT
import com.lgn.core.Constants.KEY_USER_EMAIL
import com.lgn.core.Constants.KEY_USER_PHONE
import com.lgn.core.Constants.KEY_USER_PINCODE
import com.lgn.core.Constants.KEY_USER_ROLE
import com.lgn.core.Constants.KEY_USER_STATE
import com.lgn.domain.model.*
import com.lgn.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import retrofit2.HttpException
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
        val apiService = ApiService.getInstance()
        try {
            val authResult = apiService.login(LoginRequest(userCode, password))

            /* val mockAuthResult = AuthResult(
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
             )*/
            //delay(3000L)
            val dataStore = LocalDataStore(context)
            dataStore.saveBooleanValue(KEY_IS_LOGGED, true)
            dataStore.saveStringValue(
                KEY_USERNAME,
                (authResult.user?.userFirstName ?: "") + (authResult.user?.userLastName ?: "")
            )
            dataStore.saveStringValue(KEY_USER_EMAIL, authResult.user?.userEmail ?: "")
            dataStore.saveStringValue(KEY_USER_PHONE, authResult.user?.userPhone ?: "")
            dataStore.saveStringValue(KEY_USER_ADDRESS, authResult.user?.userAddress ?: "")
            dataStore.saveStringValue(KEY_USER_BLOCK, authResult.user?.userBlock ?: "")
            dataStore.saveStringValue(KEY_USER_DISTRICT, authResult.user?.userDistrict ?: "")
            dataStore.saveStringValue(KEY_USER_PINCODE, authResult.user?.userPin ?: "")
            dataStore.saveStringValue(KEY_USER_STATE, authResult.user?.userState ?: "")
            dataStore.saveStringValue(KEY_USER_AADHAR, authResult.user?.userAadhar ?: "")
            dataStore.saveStringValue(KEY_USER_ROLE, authResult.user?.role ?: "")
            dataStore.saveBooleanValue(KEY_IS_LOGGED, true)

            authResult.accessToken?.let {
                dataStore.saveStringValue(KEY_ACCESS_TOKEN, it)
            }
            emit(Response.Success(authResult))
        } catch (e: Exception) {
            try {
                if (e is HttpException) {
                    val json = JSONObject(e.response()?.errorBody()?.string())
                    val errorMessage = json?.getString("message")

                    emit(Response.Error(errorMessage ?: ""))
                } else {
                    emit(Response.Error(e.message ?: e.toString()))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }


        }
    }

    override fun getUserProfileDetails(context: Context): UserProfile {
        val dataStore = LocalDataStore(context)

        val userName =
            runBlocking { dataStore.getStringValue(Constants.KEY_USERNAME).first() } ?: ""

        val email =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_EMAIL).first() } ?: ""

        val phone =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_PHONE).first() } ?: ""

        val address =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_ADDRESS).first() } ?: ""

        val block =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_BLOCK).first() } ?: ""

        val district =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_DISTRICT).first() } ?: ""

        val state =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_STATE).first() } ?: ""

        val pincode =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_PINCODE).first() } ?: ""

        val aadhar =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_AADHAR).first() } ?: ""

        val role =
            runBlocking { dataStore.getStringValue(Constants.KEY_USER_ROLE).first() } ?: ""


        return UserProfile(
            userName = userName,
            userAadhar = aadhar,
            role = role,
            userPin = pincode,
            userState = state,
            userDistrict = district,
            userBlock = block,
            userAddress = address,
            userPhone = phone,
            userEmail = email

        )
    }

    override fun updateStudentMetrics(
        context: Context,
        studentMerticsResponse: StudentMerticsResponse
    ) = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()

        try {
            if (studentMerticsResponse.id != null) {
                // Update Metric
                val authResult = apiService.updateStudentMetrics(
                    studentMerticsResponse.id ?: "",
                    studentMerticsResponse
                )
                emit(Response.Success(authResult))
            } else {
                // Add Metric
                val authResult = apiService.addStudentMetrics(studentMerticsResponse)
                emit(Response.Success(authResult))
            }
            //delay(1000L)
            //emit(Response.Success(studentMerticsResponse))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }

    }

    override fun fetchTeam(context: Context): Flow<Response<TeamData>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {
            //val teamResponse = apiService.fetchTeam()

            val studentList = mutableListOf<StudentData>()
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Nikita",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Rohit",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Nikita",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Rohit",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Nikita",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Rohit",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Nikita",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 1
                )
            )

            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Rohit",
                    userPhone = "123456789",
                    role = "Graduate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 0
                )
            )
            studentList.add(
                StudentData(
                    id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                    userFirstname = "Nikita",
                    userPhone = "123456789",
                    role = "Associate",
                    batch = "2020-01-01T00:00:00.000Z",
                    status = 1
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
            val apiService = ApiService.getInstance()
            try {
                val userResult = apiService?.fetchStudents(monthYear)
                val students = userResult?.users as ArrayList

                /*val studentList = mutableListOf<Users>()
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
                )*/

                emit(Response.Success(students))
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
        status: Int,
        role: String
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {
            val updateStudentResponse = apiService.changeToGraduate(
                userId, UpdateStudentResponse(
                    Status = status,
                    roleId = "Associate"
                )
            )

            /* val response = UpdateStudentResponse(
                  Status = status
              )
              delay(2000L)*/
            emit(Response.Success(updateStudentResponse))
        } catch (e: Exception) {
            try {
                if (e is HttpException) {
                    val json = JSONObject(e.response()?.errorBody()?.string())
                    val errorMessage = json?.getString("message")

                    emit(Response.Error(errorMessage ?: ""))
                } else {
                    emit(Response.Error(e.message ?: e.toString()))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }


        }
    }


    override fun fetchStudentProfileMetrics(
        context: Context,
        userId: String,
        year: String
    ): Flow<Response<StudentProfileMerticsResponse>> =
        flow {
            emit(Response.Loading)
            val apiService = ApiService.getInstance()
            try {
                //val studentProfileMerticsResponse = apiService.fetchStudentProfileMetrics(userId, year)

                val metricsList = mutableListOf<Metrics>() as ArrayList

                metricsList.add(
                    Metrics(
                        id = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        userId = "4d95797e-1f69-4ffa-b7dd-23b245ebe6bc",
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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
                        monthyear = "$year-01-01T00:00:00.000Z",
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