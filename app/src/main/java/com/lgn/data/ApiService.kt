package com.lgn.data

import com.lgn.domain.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(userCode: String, password: String): AuthResult

    @POST("user/auth")
    suspend fun getTeam(userCode: String, password: String): TeamData

    @GET("users/{monthyear}")
    suspend fun fetchStudents(@Path(value = "monthyear", encoded = true) monthYear: String): UsersMetricsResponse

    @GET("metrics/{id}")
    suspend fun fetchStudentMetrics(@Path(value = "id", encoded = true) id: String): StudentMerticsResponse

    @POST("metrics")
    suspend fun updateStudentMetrics(@Body studentMerticsResponse: StudentMerticsResponse): StudentMerticsResponse

    @GET("metrics/user/{id}")
    suspend fun fetchStudentProfileMetrics(@Path(value = "id", encoded = true) id: String, year: String): StudentProfileMerticsResponse

    @PUT("user/{id}")
    suspend fun changeToGraduate(@Path(value = "id", encoded = true) id: String, @Body changeToGraduate: UpdateStudentResponse): UpdateStudentResponse

    @PUT("user")
    suspend fun addStudent(@Body student: UpdateStudentResponse): UpdateStudentResponse

    companion object {
        var apiService: ApiService? = null

        fun getInstance(): ApiService {
            if (apiService ==null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://api.lgnindia.org/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}