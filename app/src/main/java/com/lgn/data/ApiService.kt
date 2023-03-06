package com.lgn.data

import com.lgn.domain.model.AuthResult
import com.lgn.domain.model.TeamData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(userCode: String, password: String): AuthResult

    @POST("user/auth")
    suspend fun getTeam(userCode: String, password: String): TeamData

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