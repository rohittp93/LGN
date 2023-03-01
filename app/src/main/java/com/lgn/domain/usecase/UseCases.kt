package com.lgn.domain.usecase

data class UseCases(
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,
    val checkAuthStatus: CheckAuthStatus,
    )