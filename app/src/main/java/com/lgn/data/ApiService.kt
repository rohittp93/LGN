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
        @Body studentMerticsResponse: StudentMerticsResponse
    ): StudentMerticsResponse

    @POST("metrics")
    suspend fun addStudentMetrics(@Body studentMerticsResponse: StudentMerticsResponse): StudentMerticsResponse

    @GET("metrics/user/{id}")
    suspend fun fetchStudentProfileMetrics(
        @Path(value = "id", encoded = true) id: String,
        @Query("year") year: String
    ): StudentProfileMerticsResponse

    @PUT("user/{id}")
    suspend fun changeToGraduate(
        @Path(value = "id", encoded = true) id: String,
        @Body changeToGraduate: UpdateStudentResponse
    ): UpdateStudentResponse


    @PUT("metrics/{id}")
    suspend fun update(
        @Path(value = "id", encoded = true) id: String,
        @Body updateStudentResponse: UpdateStudentResponse
    ): UpdateStudentResponse

    @PUT("user")
    suspend fun addStudent(@Body student: UpdateStudentResponse): UpdateStudentResponse


    companion object {
        var apiService: ApiService? = null
        /*val bearerToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYjE5YTc2OTgtYmI5MC0xMWVkLTlhMzMtOTgxMThkOGYyNTBlIiwicm9sZSI6IkFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiIsImlhdCI6MTY3ODA0NzYwM30.gBXJyYftMsGAsOSGWETPwuPYxrwUmcNwCCDBCqm-fn0"
*/

        private val dataStore = LocalDataStore(LgnApp.appContext)
        val accessToken =
            runBlocking { dataStore.getStringValue(Constants.KEY_ACCESS_TOKEN).first() } ?: ""


        fun addClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()

            val builder = httpClient.addInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.5563.57 Mobile Safari/537.36")
                        .build()
                chain.proceed(request)
            })
            return builder.build()
        }

        fun getInstance(): ApiService {
            if (accessToken.isEmpty()) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://api.lgnindia.org/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            } else {

                if (apiService == null) {
                    apiService = Retrofit.Builder()
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