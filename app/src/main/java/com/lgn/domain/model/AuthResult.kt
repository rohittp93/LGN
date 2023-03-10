package com.lgn.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthResult(
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("user") var user: User? = User()
) : Parcelable

@Parcelize
data class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("user_name") var userName: String? = null,
    @SerializedName("user_email") var userEmail: String? = null,
    @SerializedName("user_phone") var userPhone: String? = null,
    @SerializedName("user_address") var userAddress: String? = null,
    @SerializedName("user_block") var userBlock: String? = null,
    @SerializedName("user_district") var userDistrict: String? = null,
    @SerializedName("user_pin") var userPin: String? = null,
    @SerializedName("user_state") var userState: String? = null,
    @SerializedName("user_aadhar") var userAadhar: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("status") var status: Int? = null
): Parcelable