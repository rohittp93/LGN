package com.lgn.domain.model

import com.google.gson.annotations.SerializedName

data class StudentMetricResponse(
    @SerializedName("users" ) var users : ArrayList<Users> = arrayListOf()
)

data class Users (
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("user_id"   ) var userId    : String? = null,
    @SerializedName("monthyear" ) var monthyear : String? = null,
    @SerializedName("user_name" ) var userName  : String? = null,
    @SerializedName("role"      ) var role      : String? = null
)