package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.UpdateStudentResponse
import com.lgn.domain.repository.Repository

class AddStudent (
    private val repository: Repository
) {
    operator fun invoke(context: Context, student: UpdateStudentResponse) = repository.addStudent(context, student)
}