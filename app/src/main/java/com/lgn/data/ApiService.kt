package com.lgn.data

import com.lgn.domain.model.AuthResult
import com.lgn.domain.model.StudentMetricResponse
import com.lgn.domain.model.TeamData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(userCode: String, password: String): AuthResult

    @POST("user/auth")
    suspend fun getTeam(userCode: String, password: String): TeamData


    @GET("users/{monthyear}")
    suspend fun fetchStudents(@Path(value = "monthyear", encoded = true) monthYear: String): StudentMetricResponse

    companion object {
        var apiService: ApiService? = null

        fun getInstance(): ApiService {
            if (apiService ==null) {
                apiService = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}