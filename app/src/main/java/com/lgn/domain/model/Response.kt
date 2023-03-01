package com.lgn.domain.model

sealed class Response<out T> {
    object Idle: Response<Nothing>()

    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Error(
        val message: String
    ): Response<Nothing>()
}