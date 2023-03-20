package com.lgn.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UsersMetricsResponse(
    @SerializedName("users" ) var users : ArrayList<Users> = arrayListOf()
)

data class Users (
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("user_id"   ) var userId    : String? = null,
    @SerializedName("monthyear" ) var monthyear : String? = null,
    @SerializedName("user_name" ) var userName  : String? = null,
    @SerializedName("user_firstname" ) var userFirstname  : String? = null,
    @SerializedName("user_lastname" ) var userLastname  : String? = null,
    @SerializedName("role"      ) var role      : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeString(monthyear)
        parcel.writeString(userName)
        parcel.writeString(userFirstname)
        parcel.writeString(userLastname)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}