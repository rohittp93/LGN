package com.lgn.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamData (
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
    @SerializedName("status") var status: Int? = null,
    @SerializedName("associate") var associate: List<StudentData> = listOf()
) : Parcelable


@Parcelize
data class StudentData (
    @SerializedName("id") var id: String? = null,
    @SerializedName("user_name") var userName: String? = null,
    @SerializedName("user_email") var userEmail: String? = null,
    @SerializedName("user_phone") var userPhone: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("batch") var batch: String? = null,
    @SerializedName("status") var status: Int? = null,
) : Parcelable