package com.lgn.domain.model

import com.google.gson.annotations.SerializedName

data class StudentProfileMerticsResponse (  @SerializedName("metrics" ) var metrics : ArrayList<Metrics> = arrayListOf())

data class Metrics (
    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("user_id"    ) var userId    : String? = null,
    @SerializedName("monthyear"  ) var monthyear : String? = null,
    @SerializedName("ev"         ) var ev        : Int?    = null,
    @SerializedName("de"         ) var de        : Int?    = null,
    @SerializedName("jb"         ) var jb        : Int?    = null,
    @SerializedName("aa"         ) var aa        : Int?    = null,
    @SerializedName("p"          ) var p         : Int?    = null,
    @SerializedName("e"          ) var e         : Int?    = null,
    @SerializedName("a"          ) var a         : Int?    = null,
    @SerializedName("c"          ) var c         : Int?    = null,
    @SerializedName("ed"         ) var ed        : Int?    = null,
    @SerializedName("is_deleted" ) var isDeleted : Int?    = null
)