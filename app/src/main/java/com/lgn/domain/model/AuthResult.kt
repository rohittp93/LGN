package com.lgn.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
//TODO: Change data class as per login response variables
@Parcelize
data class AuthResult (
    var eventName: String = "",
    var coupleCount: Int = 0,
    var stagCount: Int = 0,
    var totalPrice: Double = 0.0,
    var eventId: String = "",
    var userId: String = "",
    var userName: String = "",
) : Parcelable