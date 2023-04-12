package com.lgn.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    @SerializedName("id") var id: String? = null,
    var userName: String? = null,
    var userEmail: String? = null,
    var userPhone: String? = null,
    var userAddress: String? = null,
    var userBlock: String? = null,
    var userDistrict: String? = null,
    var userPin: String? = null,
    var userState: String? = null,
    var userAadhar: String? = null,
    var role: String? = null,

    var user_dob: String? = null,
    var user_city: String? = null,
    var training_city: String? = null,
    var training_district: String? = null,
    var training_pin: String? = null,
    var training_state: String? = null,

) : Parcelable
