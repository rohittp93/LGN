package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository

class FetchStudents (
    private val repository: Repository
) {
    operator fun invoke(context: Context, monthYear: String) = repository.fetchStudents(context, monthYear)
}