package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.models.request.ViaturaRequest
import com.example.e_ocorrencias.data.models.response.Viatura
import com.example.e_ocorrencias.data.models.response.page.ViaturasResponse
import com.example.e_ocorrencias.data.remote.ViaturaService
import com.example.e_ocorrencias.utils.ErrorHandler
import javax.inject.Inject

class ViaturaRepository @Inject constructor(
    private val viaturaService: ViaturaService,
    private val errorHandler: ErrorHandler

) {
    private val errorViaturaNaoEncontrada = "Viatura não encontrada"
    private val errorFalhaConexao = "Falha ao conectar ao servidor"
    private val erroFalhaCarregarViatura = "Falha ao carregar viatura"
    private val errorListaViaturas = "Lista de viaturas vazia"
    private val errorSalvarViatura = "Falha ao salvar viatura"

    suspend fun getViaturas(page: Int): Result<ViaturasResponse> {
        return try {
            val response = viaturaService.getViaturas(page)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception(errorListaViaturas))
            } else {
                val errorMessage =
                    errorHandler.parse(response.errorBody(), erroFalhaCarregarViatura)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            throw Exception("$errorFalhaConexao: ${e.message}")
        }
    }

    suspend fun searchViaturas(
        prefixo: String? = null,
        status: String? = null
    ): Result<ViaturasResponse> {
        return try {
            val response = viaturaService.searchViaturas(
                prefixo = prefixo,
                status = status
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = errorHandler.parse(response.errorBody(), errorListaViaturas)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorListaViaturas: ${e.message}"))
        }
    }

    suspend fun getViaturaById(id: String): Result<Viatura> {
        return try {
            val response = viaturaService.getViaturaById(id)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception(errorViaturaNaoEncontrada))
            } else {
                val errorMessage =
                    errorHandler.parse(response.errorBody(), erroFalhaCarregarViatura)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorFalhaConexao: ${e.message}"))
        }
    }

    suspend fun createViatura(viaturaRequest: ViaturaRequest): Result<Viatura> {
        return try {
            val response = viaturaService.createViatura(viaturaRequest)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception(errorSalvarViatura))
            } else {
                val errorMessage = errorHandler.parse(response.errorBody(), errorSalvarViatura)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorFalhaConexao: ${e.message}"))
        }
    }
}