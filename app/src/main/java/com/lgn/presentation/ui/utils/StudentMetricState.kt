package com.lgn.presentation.ui.utils

import com.google.gson.annotations.SerializedName
import java.util.*

data class StudentMetricState(
    var id: String? = null,
    var userId: String? = null,
    var monthyear: String? = null,
    var ev: Int? = 0,
    var de: Int? = 0,
    var jb: Int? = 0,
    var aa: Int? = 0,
    var p: Int? = 0,
    var e: Int? = 0,
    var a: Int? = 0,
    var c: Int? = 0,
    var ed: Int? = 0,
    var isDeleted: Int? = 0
)