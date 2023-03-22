package com.lgn.domain.model

import com.google.gson.annotations.SerializedName

data class UpdateStudentMerticsRequest (
    @SerializedName("user_id"    ) var userId    : String? = null,
    @SerializedName("monthyear"  ) var monthyear : String? = null,
    @SerializedName("ev"         ) var ev        : Int?    = 0,
    @SerializedName("de"         ) var de        : Int?    = 0,
    @SerializedName("jb"         ) var jb        : Int?    = 0,
    @SerializedName("aa"         ) var aa        : Int?    = 0,
    @SerializedName("p"          ) var p         : Int?    = 0,
    @SerializedName("e"          ) var e         : Int?    = 0,
    @SerializedName("a"          ) var a         : Int?    = 0,
    @SerializedName("c"          ) var c         : Int?    = 0,
    @SerializedName("ed"         ) var ed        : Int?    = 0,
    @SerializedName("is_deleted" ) var isDeleted : Int?    = 0

)