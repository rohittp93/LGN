package com.lgn.domain.model

import com.google.gson.annotations.SerializedName

data class UpdateStudentResponse (
    @SerializedName("id") var id: String? = null,
    @SerializedName("user_firstname") var userFirstname: String? = null,
    @SerializedName("user_lastname") var userLastname: String? = null,
    @SerializedName("user_email") var userEmail: String? = null,
    @SerializedName("user_phone") var userPhone: String? = null,
    @SerializedName("user_address") var userAddress: String? = null,
    @SerializedName("user_block") var userBlock: String? = null,
    @SerializedName("user_district") var userDistrict: String? = null,
    @SerializedName("user_state") var userState: String? = null,
    @SerializedName("user_pin") var userPin: String? = null,
    @SerializedName("user_aadhar") var userAadhar: String? = null,
    @SerializedName("role_id") var roleId: String? = null,
    @SerializedName("batch") var batch: String? = null,
    @SerializedName("trainer_id") var trainerId: String? = null,
    @SerializedName("Status") var Status: Int? = null
)