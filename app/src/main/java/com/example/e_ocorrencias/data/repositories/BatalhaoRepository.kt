package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.models.request.BatalhaoRequest
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.data.models.response.page.BatalhaoResponse
import com.example.e_ocorrencias.data.remote.BatalhaoService
import javax.inject.Inject


class BatalhaoRepository @Inject constructor(
    private val batalhaoService: BatalhaoService
) {
    suspend fun getAllBatalhoes(page: Int): BatalhaoResponse {
        return try {
            batalhaoService.getAllBatalhoes(page)
        } catch (e: Exception) {
            throw Exception("Falha na conexão: ${e.message}")
        }
    }

    suspend fun createBatalhao(batalhaoRequest: BatalhaoRequest): Batalhao {
        return try {
            batalhaoService.createBatalhao(batalhaoRequest)
        } catch (e: Exception) {
            throw Exception("Falha ao criar batalhao:, $ { e.message }")
        }
    }

    suspend fun getBatalhaoById(id: String): Batalhao {
        return try {
            batalhaoService.getBatalhaoById(id)
        } catch (e: Exception) {
            throw Exception("Falha ao buscar batalhão: ${e.message}", e)
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
        cep: String? = null,
    ): Batalhao {

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
        return batalhaoService.updateBatalhao(id, batalhaoRequest)
    }

    suspend fun searchBatalhoes(nome: String? = null): BatalhaoResponse {
        return try {
            val response = batalhaoService.searchBatalhoes(nome = nome)
            response
        } catch (e: Exception) {
            throw Exception("Falha ao buscar batalhoes: ${e.message}")
        }
    }

    suspend fun deleteBatalhao(id: String): Result<Unit> {
        return try {
            val response = batalhaoService.deleteBatalhao(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure((Exception("Falha ao deletar batalhão: ${response.message()}")))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao conectar ao servidor: ${e.message}"))
        }
    }
}