package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.repository.Repository

class UpdateStudentMetrics (
    private val repository: Repository
) {
    operator fun invoke(context: Context, studentMetrics: StudentMerticsResponse) = repository.updateStudentMetrics(context, studentMetrics)
}