package com.lgn.data

import android.content.Context
import android.widget.Toast
import com.lgn.core.Constants
import com.lgn.core.Constants.KEY_ACCESS_TOKEN
import com.lgn.core.Constants.KEY_CITY
import com.lgn.core.Constants.KEY_DOB
import com.lgn.core.Constants.KEY_IS_LOGGED
import com.lgn.core.Constants.KEY_TRAINING_CITY
import com.lgn.core.Constants.KEY_TRAINING_DISTRICT
import com.lgn.core.Constants.KEY_TRAINING_PIN
import com.lgn.core.Constants.KEY_TRAINING_STATE
import com.lgn.core.Constants.KEY_USERID
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


@Singleton
class RemoteRepositoryImpl @Inject constructor() : Repository {

    override suspend fun loginWithUserCodeAndPassword(
        userCode: String,
        password: String,
        context: Context,
        scope: CoroutineScope
    ) = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance(true)
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
                (authResult.user?.userFirstName ?: "") + " " + (authResult.user?.userLastName ?: "")
            )
            dataStore.saveStringValue(KEY_USER_EMAIL, authResult.user?.userEmail ?: "")
            dataStore.saveStringValue(KEY_USERID, authResult.user?.id ?: "")
            dataStore.saveStringValue(KEY_USER_PHONE, authResult.user?.userPhone ?: "")
            dataStore.saveStringValue(KEY_USER_ADDRESS, authResult.user?.userAddress ?: "")
            dataStore.saveStringValue(KEY_USER_BLOCK, authResult.user?.userBlock ?: "")
            dataStore.saveStringValue(KEY_USER_DISTRICT, authResult.user?.userDistrict ?: "")
            dataStore.saveStringValue(KEY_USER_PINCODE, authResult.user?.userPin ?: "")
            dataStore.saveStringValue(KEY_USER_STATE, authResult.user?.userState ?: "")
            dataStore.saveStringValue(KEY_USER_AADHAR, authResult.user?.userAadhar ?: "")
            dataStore.saveStringValue(KEY_USER_ROLE, authResult.user?.role ?: "")
            dataStore.saveBooleanValue(KEY_IS_LOGGED, true)

            dataStore.saveStringValue(KEY_DOB, authResult.user?.dob ?: "")
            dataStore.saveStringValue(KEY_CITY, authResult.user?.city ?: "")
            dataStore.saveStringValue(KEY_TRAINING_CITY, authResult.user?.training_city ?: "")
            dataStore.saveStringValue(KEY_TRAINING_DISTRICT, authResult.user?.training_district ?: "")
            dataStore.saveStringValue(KEY_TRAINING_PIN, authResult.user?.training_pin ?: "")
            dataStore.saveStringValue(KEY_TRAINING_STATE, authResult.user?.training_state ?: "")

            authResult.accessToken?.let {
                dataStore.saveStringValue(KEY_ACCESS_TOKEN, it)
            }
            delay(2000L)
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

        val dob =
            runBlocking { dataStore.getStringValue(Constants.KEY_DOB).first() } ?: ""

        val city =
            runBlocking { dataStore.getStringValue(Constants.KEY_CITY).first() } ?: ""

        val trainingCity =
            runBlocking { dataStore.getStringValue(Constants.KEY_TRAINING_CITY).first() } ?: ""

        val trainingDistrict =
            runBlocking { dataStore.getStringValue(Constants.KEY_TRAINING_DISTRICT).first() } ?: ""

        val trainingPin =
            runBlocking { dataStore.getStringValue(Constants.KEY_TRAINING_PIN).first() } ?: ""

        val trainingState =
            runBlocking { dataStore.getStringValue(Constants.KEY_TRAINING_STATE).first() } ?: ""

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
            userEmail = email,
            user_dob = dob,
            user_city = city,
            training_city = trainingCity,
            training_district = trainingDistrict,
            training_pin = trainingPin,
            training_state = trainingState,
        )
    }

    override fun updateStudentMetrics(
        context: Context,
        id: String,
        studentMerticsResponse: StudentMerticsResponse
    ) = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()

        try {
            if (studentMerticsResponse.id != null) {
                // Update Metric
                val authResult = apiService.updateStudentMetrics(
                    id ?: "",
                    UpdateStudentMerticsRequest(
                        userId = studentMerticsResponse.userId,
                        monthyear = studentMerticsResponse.monthyear,
                        ev = studentMerticsResponse.ev,
                        de = studentMerticsResponse.de,
                        jb = studentMerticsResponse.jb,
                        aa = studentMerticsResponse.aa,
                        p = studentMerticsResponse.p,
                        e = studentMerticsResponse.e,
                        a = studentMerticsResponse.a,
                        c = studentMerticsResponse.c,
                        ed = studentMerticsResponse.ed,
                        isDeleted = studentMerticsResponse.isDeleted,
                    )
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
            val teamResponse = apiService.fetchTeam()

            val dataStore = LocalDataStore(context)
            dataStore.saveBooleanValue(KEY_IS_LOGGED, true)
            dataStore.saveStringValue(
                KEY_USERNAME,
                (teamResponse.userFirstName ?: "") + " " + (teamResponse.userLastName ?: "")
            )
            dataStore.saveStringValue(KEY_USER_EMAIL, teamResponse.userEmail ?: "")
            dataStore.saveStringValue(KEY_USERID, teamResponse.id ?: "")
            dataStore.saveStringValue(KEY_USER_PHONE, teamResponse.userPhone ?: "")
            dataStore.saveStringValue(KEY_USER_ADDRESS, teamResponse.userAddress ?: "")
            dataStore.saveStringValue(KEY_USER_BLOCK, teamResponse.userBlock ?: "")
            dataStore.saveStringValue(KEY_USER_DISTRICT, teamResponse.userDistrict ?: "")
            dataStore.saveStringValue(KEY_USER_PINCODE, teamResponse.userPin ?: "")
            dataStore.saveStringValue(KEY_USER_STATE, teamResponse.userState ?: "")
            dataStore.saveStringValue(KEY_USER_AADHAR, teamResponse.userAadhar ?: "")
            dataStore.saveStringValue(KEY_USER_ROLE, teamResponse.role ?: "")

            dataStore.saveStringValue(KEY_DOB, teamResponse.dob ?: "")
            dataStore.saveStringValue(KEY_CITY, teamResponse.city ?: "")
            dataStore.saveStringValue(KEY_TRAINING_CITY, teamResponse.training_city ?: "")
            dataStore.saveStringValue(KEY_TRAINING_DISTRICT, teamResponse.training_district ?: "")
            dataStore.saveStringValue(KEY_TRAINING_PIN, teamResponse.training_pin ?: "")
            dataStore.saveStringValue(KEY_TRAINING_STATE, teamResponse.training_state ?: "")

            emit(Response.Success(teamResponse))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }


    override fun fetchStudents(context: Context, monthYear: String): Flow<Response<List<Users>>> =
        flow {
            emit(Response.Loading)
            val apiService = ApiService.getInstance()
            try {
                val userResult = apiService?.fetchStudents(monthYear, 100)
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
                val studentMerticsResponse = apiService.fetchStudentMetrics(id)

                /* var studentMerticsResponse = StudentMerticsResponse(
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

                 delay(1000L)*/
                emit(Response.Success(studentMerticsResponse))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

    override fun addStudent(
        context: Context,
        student: UpdateStudentResponse,
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {

            val dataStore = LocalDataStore(context)
            val trainerId =
                runBlocking { dataStore.getStringValue(Constants.KEY_USERID).first() } ?: ""

            student.trainerId = trainerId
            val addStudentResponse = apiService.addStudent(student)

            emit(Response.Success(addStudentResponse))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun changeToGraduate(
        context: Context,
        user: StudentData
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {
            /* val changeToGraduateResponse = apiService.changeToGraduate(
                 userId, UpdateStudentResponse(
                     roleId = "Graduate",
                     batch = batch
                 )
             )*/

            val dataStore = LocalDataStore(context)
            val trainerId =
                runBlocking { dataStore.getStringValue(Constants.KEY_USERID).first() } ?: ""

            val updateStudentResponse = apiService.updateUser(
                user.id ?: "", UpdateStudentResponse(
                    status = user.status,
                    userFirstname = user.userFirstname,
                    userEmail = user.userEmail,
                    userLastname = user.userLastname,
                    userPhone = user.userPhone,
                    batch = user.batch,
                    roleId = "Graduate",
                    trainerId = trainerId
                )
            )

            /* val changeToGraduateResponse = UpdateStudentResponse(
                 roleId = "Graduate",
                 batch = batch
             )
             delay(2000L)*/
            emit(Response.Success(updateStudentResponse))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateStudentStatus(
        context: Context,
        user: StudentData, status: Int
    ): Flow<Response<UpdateStudentResponse>> = flow {
        emit(Response.Loading)
        val apiService = ApiService.getInstance()
        try {
            val dataStore = LocalDataStore(context)
            val trainerId =
                runBlocking { dataStore.getStringValue(Constants.KEY_USERID).first() } ?: ""


            val updateStudentResponse = apiService.updateUser(
                user.id ?: "", UpdateStudentResponse(
                    status = status,
                    userFirstname = user.userFirstname,
                    userEmail = user.userEmail,
                    userLastname = user.userLastname,
                    userPhone = user.userPhone,
                    batch = user.batch,
                    roleId = user.role,
                    trainerId = trainerId
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
        userId: String
    ): Flow<Response<StudentProfileMerticsResponse>> =
        flow {
            emit(Response.Loading)
            val apiService = ApiService.getInstance()
            try {
                val studentProfileMerticsResponse =
                    apiService.fetchStudentProfileMetrics(userId)

                /*val metricsList = mutableListOf<Metrics>() as ArrayList

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

                var studentProfileMerticsResponse = StudentProfileMerticsResponse(
                    metrics = metricsList
                )

                delay(1000L)*/
                emit(Response.Success(studentProfileMerticsResponse))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

    override fun logoutUser(context: Context): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        val dataStore = LocalDataStore(context)
        dataStore.clearAllPreferences()
        emit(Response.Success(true))
    }

    override fun isUserLoggedIn(context: Context): Boolean {
        val dataStore = LocalDataStore(context)
        return runBlocking { dataStore.getBooleanValue(KEY_IS_LOGGED).first() } ?: false
    }
}