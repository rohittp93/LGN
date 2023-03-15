package com.lgn.domain.usecase

data class UseCases(
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,
    val checkAuthStatus: CheckAuthStatus,
    val validateUserCode: ValidateUserCode,
    val validatePassword: ValidatePassword,
    val fetchTeam: FetchTeam,
    val fetchStudents: FetchStudents,
    val fetchStudentMetrics: FetchStudentMetrics,
    val updateStudentMetrics: UpdateStudentMetrics,
    val addStudent: AddStudent,
    val fetchStudentProfileMetrics: FetchStudentProfileMetrics,
    val changeToGraduate: ChangeToGraduate,
    val updateStudentStatus: UpdateStudentStatus,
    )