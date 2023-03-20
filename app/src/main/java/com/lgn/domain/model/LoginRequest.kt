package com.lgn.domain.model

import com.google.gson.annotations.SerializedName
import com.lgn.domain.usecase.ValidatePassword

data class LoginRequest(
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
)
