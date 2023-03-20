package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.repository.Repository

class GetUserProfileDetails (
    private val repository: Repository
) {
    operator fun invoke(context: Context) = repository.getUserProfileDetails(context)
}