package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository

class FetchStudentMetrics (
    private val repository: Repository
) {
    operator fun invoke(context: Context, id: String) = repository.fetchStudentMetrics(context, id)
}