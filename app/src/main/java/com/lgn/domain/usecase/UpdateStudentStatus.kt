package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.repository.Repository

class UpdateStudentStatus (
    private val repository: Repository
) {
    operator fun invoke(context: Context, userId: String, status: Int) = repository.updateStudentStatus(context, userId, status)
}