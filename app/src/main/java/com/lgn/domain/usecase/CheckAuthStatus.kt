package com.lgn.domain.usecase

import com.lgn.domain.repository.Repository

class CheckAuthStatus(private val repository: Repository) {
    fun execute(): Boolean = repository.isUserLoggedIn()
}