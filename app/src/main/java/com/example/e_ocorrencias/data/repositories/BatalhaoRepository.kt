package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.models.request.BatalhaoRequest
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.data.models.response.page.BatalhaoResponse
import com.example.e_ocorrencias.data.remote.BatalhaoService
import com.example.e_ocorrencias.utils.ErrorHandler
import com.example.e_ocorrencias.utils.apiCallHandler
import javax.inject.Inject


class BatalhaoRepository @Inject constructor(
    private val batalhaoService: BatalhaoService,
    private val errorHandler: ErrorHandler
) {
    private val errorListarBatalhoes = "Lista de batalhões vazia"
    private val errorFalhaConexao = "Falha ao conectar ao servidor"
    private val errorSalvarBatalhao = "Falha ao salvar batalhao"
    private val errorBatalhaoNaoEncontrado = "Batalhao não encontrada"

    suspend fun getAllBatalhoes(page: Int): Result<BatalhaoResponse> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorListarBatalhoes
        ) {
            batalhaoService.getAllBatalhoes(page)
        }
    }

    suspend fun createBatalhao(batalhaoRequest: BatalhaoRequest): Result<Batalhao> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorSalvarBatalhao
        ) {
            batalhaoService.createBatalhao(batalhaoRequest)
        }

    }

    suspend fun getBatalhaoById(id: String): Result<Batalhao> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorBatalhaoNaoEncontrado
        ) {
            batalhaoService.getBatalhaoById(id)
        }
    }

    suspend fun updateBatalhao(
        id: String,
        nome: String? = null,
        contato: String? = null,
        rua: String? = null,
        numero: String? = null,
        complemento: String? = null,
        bairro: String? = null,
        cidade: String? = null,
        uf: String? = null,
        cep: String? = null
    ): Result<Batalhao> {
        val enderecoRequest = EnderecoRequest(
            rua = rua ?: "",
            numero = numero ?: "",
            bairro = bairro ?: "",
            cidade = cidade ?: "",
            complemento = complemento,
            uf = uf ?: "",
            cep = cep ?: ""
        )

        val batalhaoRequest = BatalhaoRequest(
            nome = nome ?: "",
            contato = contato ?: "",
            endereco = enderecoRequest
        )

        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorSalvarBatalhao
        ) {
            batalhaoService.updateBatalhao(id, batalhaoRequest)
        }
    }

    suspend fun searchBatalhoes(nome: String? = null): Result<BatalhaoResponse> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = "Nenhum batalhao encontrado"
        ) {
            batalhaoService.searchBatalhoes(nome = nome)
        }
    }

    suspend fun deleteBatalhao(id: String): Result<Unit> {
        return apiCallHandler(
            errorHandler = errorHandler,
            errorMessageOnEmpty = errorFalhaConexao
        ) {
            batalhaoService.deleteBatalhao(id)
        }
    }
}

