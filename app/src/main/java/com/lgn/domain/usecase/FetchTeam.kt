package com.lgn.domain.usecase

import android.content.Context
import com.lgn.domain.repository.Repository

class FetchTeam (
    private val repository: Repository
) {
    operator fun invoke(context: Context) = repository.fetchTeam(context)
}