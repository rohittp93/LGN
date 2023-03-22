package com.lgn.data

import android.content.Context
import com.lgn.LgnApp
import com.lgn.core.Constants
import com.lgn.domain.model.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResult

    @GET("user/{monthyear}")
    suspend fun fetchStudents(
        @Path(
            value = "monthyear",
            encoded = true
        ) monthYear: String
    ): UsersMetricsResponse


    @GET("user/auth")
    suspend fun fetchTeam(): TeamData

    @GET("metrics/{id}")
    suspend fun fetchStudentMetrics(
        @Path(
            value = "id",
            encoded = true
        ) id: String
    ): StudentMerticsResponse

    @PUT("metrics/{id}")
    suspend fun updateStudentMetrics(
        @Path(value = "id", encoded = true) id: String,
        @Body studentMerticsResponse: UpdateStudentMerticsRequest
    ): StudentMerticsResponse

    @POST("metrics")
    suspend fun addStudentMetrics(@Body studentMerticsResponse: StudentMerticsResponse): StudentMerticsResponse

    @GET("metrics/user/{id}")
    suspend fun fetchStudentProfileMetrics(
        @Path(value = "id", encoded = true) id: String
    ): StudentProfileMerticsResponse

    @PUT("user/{id}")
    suspend fun updateUser(
        @Path(value = "id", encoded = true) id: String,
        @Body changeToGraduate: UpdateStudentResponse
    ): UpdateStudentResponse

    @POST("user")
    suspend fun addStudent(@Body student: UpdateStudentResponse): UpdateStudentResponse


    companion object {
        var apiService: ApiService? = null
        var loginApiService: ApiService? = null

        fun addClient(): OkHttpClient {
            val dataStore = LocalDataStore(LgnApp.appContext)
            val accessToken =
                runBlocking { dataStore.getStringValue(Constants.KEY_ACCESS_TOKEN).first() } ?: ""

            val httpClient = OkHttpClient.Builder()

            val builder = httpClient.addInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build()
                chain.proceed(request)
            })
            return builder.build()
        }


        fun addUserAgentClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()

            val builder = httpClient.addInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader(
                            "User-Agent",
                            "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.5563.57 Mobile Safari/537.36"
                        )
                        .build()
                chain.proceed(request)
            })
            return builder.build()
        }

        fun getInstance(forLogin: Boolean? = false): ApiService {
            if (forLogin == true) {
                loginApiService = Retrofit.Builder()
                    .client(addUserAgentClient())
                    .baseUrl("http://api.lgnindia.org/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
                return loginApiService!!
            } else {
                if (apiService == null) {
                    apiService = Retrofit.Builder()
                        .client(addUserAgentClient())
                        .client(addClient())
                        .baseUrl("http://api.lgnindia.org/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(ApiService::class.java)
                }
            }
            return apiService!!
        }
    }
}