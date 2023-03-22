package com.lgn.domain.model

data class StudentFilter(
    var userType: String? = "Show All",
    var batch: String? = "",
    var statusType: String? = "Both",
)
