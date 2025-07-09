package com.example.e_ocorrencias.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> apiCallHandler(
    errorHandler: ErrorHandler,
    errorMessageOnEmpty: String = "Resposta vazia",
    apiCall: suspend () -> Response<T>
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception(errorMessageOnEmpty))
            } else {
                val errorMessage = errorHandler.parse(response.errorBody(), "Erro na resposta")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception(errorHandler.parse(null, "Falha ao conectar ao servidor"), e))
        }
    }
}