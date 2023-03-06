package com.lgn.domain.usecase

data class UseCases(
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,
    val checkAuthStatus: CheckAuthStatus,
    val validateUserCode: ValidateUserCode,
    val validatePassword: ValidatePassword,
    val fetchTeam: FetchTeam,
    )