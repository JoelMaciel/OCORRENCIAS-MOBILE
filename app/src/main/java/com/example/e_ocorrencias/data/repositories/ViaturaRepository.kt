package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.models.request.ViaturaRequest
import com.example.e_ocorrencias.data.models.response.Viatura
import com.example.e_ocorrencias.data.models.response.page.ViaturasResponse
import com.example.e_ocorrencias.data.remote.ViaturaService
import com.example.e_ocorrencias.utils.ErrorHandler
import com.example.e_ocorrencias.utils.apiCallHandler
import javax.inject.Inject

class ViaturaRepository @Inject constructor(
    private val viaturaService: ViaturaService,
    private val errorHandler: ErrorHandler

) {
    private val errorViaturaNaoEncontrada = "Viatura não encontrada"
    private val errorListarViaturas = "Lista de viaturas vazia"
    private val errorSalvarViatura = "Falha ao salvar viatura"

    suspend fun getAllViaturas(page: Int): Result<ViaturasResponse> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorListarViaturas
        ) {
            viaturaService.getViaturas(page)
        }
    }

    suspend fun searchViaturas(
        prefixo: String? = null,
        status: String? = null
    ): Result<ViaturasResponse> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorListarViaturas
        ) {
            viaturaService.searchViaturas(
                prefixo = prefixo,
                status = status
            )
        }
    }

    suspend fun getViaturaById(id: String): Result<Viatura> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorViaturaNaoEncontrada
        ) {
            viaturaService.getViaturaById(id)
        }
    }

    suspend fun createViatura(viaturaRequest: ViaturaRequest): Result<Viatura> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorSalvarViatura
        ) {
            viaturaService.createViatura(viaturaRequest)
        }
    }
}