package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.data.models.request.OcorrenciaCreateRequest
import com.example.e_ocorrencias.data.models.request.OcorrenciaUpdateRequest
import com.example.e_ocorrencias.data.models.response.OcorrenciaDetails
import com.example.e_ocorrencias.data.models.response.page.OcorrenciasResponse
import com.example.e_ocorrencias.data.remote.OcorrenciaService
import com.example.e_ocorrencias.utils.ErrorHandler
import org.json.JSONObject
import javax.inject.Inject

class OcorrenciaRepository @Inject constructor(
    private val ocorrenciaService: OcorrenciaService,
    private val errorHandler: ErrorHandler
) {
    private val errorListaOcorrenciasVazia = "Lista de ocorrencias vazia"
    private val errorFalhaCarregarOcorrencia = "Falha ao carregar ocorrências"
    private val errorConectarServidor = "Erro ao conectar ao servidor"
    private val errorFalhaCriarOcorrencia = "Falha ao criar ocorrência"
    private val errorFalhaStatus = "Falha ao atualizar o status"

    suspend fun getOcorrencias(page: Int): Result<OcorrenciasResponse> {
        return try {
            val response = ocorrenciaService.getOcorrencias(page)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception(errorListaOcorrenciasVazia))
            } else {
                val errorMessage =
                    errorHandler.parse(response.errorBody(), errorFalhaCarregarOcorrencia)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorConectarServidor: ${e.message}"))
        }
    }

    suspend fun createOcorrencia(ocorrenciaRequest: OcorrenciaCreateRequest): OcorrenciaDetails {
        return try {
            ocorrenciaService.createOcorrencia(ocorrenciaRequest)
        } catch (e: Exception) {
            throw Exception("$errorFalhaCriarOcorrencia: ${e.message}")
        }
    }

    suspend fun searchOcorrencia(
        mOcorrencia: String? = null,
        prefixoViatura: String? = null,
        dataHoraInicial: String? = null,
        dataHoraFinal: String? = null,
        cidade: String? = null,
        bairro: String? = null,
    ): OcorrenciasResponse {
        return try {
            val response = ocorrenciaService.searchOcorrencia(
                mOcorrencia = mOcorrencia,
                prefixoViatura = prefixoViatura,
                dataHoraInicial = dataHoraInicial,
                dataHoraFinal = dataHoraFinal,
                cidade = cidade,
                bairro = bairro
            )
            response
        } catch (e: Exception) {
            throw Exception("$errorFalhaCarregarOcorrencia: ${e.message}")
        }
    }

    suspend fun getOcorrenciaById(id: String): OcorrenciaDetails {
        return try {
            ocorrenciaService.getOcorrenciaById(id)
        } catch (e: Exception) {
            throw Exception("$errorFalhaCarregarOcorrencia: ${e.message}")
        }
    }

    suspend fun updateOcorrencia(
        id: String,
        mOcorrencia: String,
        dataHoraInicial: String,
        dataHoraFinal: String,
        tipoOcorrencia: String,
        artigo: String,
        resumo: String,
        rua: String,
        numero: String,
        complemento: String?,
        bairro: String,
        cidade: String,
        uf: String,
        cep: String
    ): OcorrenciaDetails {
        val updateRequest = OcorrenciaUpdateRequest(
            mOcorrencia = mOcorrencia,
            dataHoraInicial = dataHoraInicial,
            dataHoraFinal = dataHoraFinal,
            tipoOcorrencia = tipoOcorrencia,
            artigo = artigo,
            resumo = resumo,
            endereco = EnderecoRequest(
                rua = rua,
                numero = numero,
                complemento = complemento,
                bairro = bairro,
                cidade = cidade,
                uf = uf,
                cep = cep
            )
        )
        return ocorrenciaService.updateOcorrencia(id, updateRequest)
    }

    suspend fun statusConcluded(id: String): Result<Unit> {
        return try {
            val response = ocorrenciaService.statusConcluded(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    json.getString("message")
                } catch (e: Exception) {
                    errorFalhaStatus
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorConectarServidor: ${e.message}"))
        }
    }

    suspend fun statusCanceled(id: String): Result<Unit> {
        return try {
            val response = ocorrenciaService.statusCanceled(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    json.getString("message")
                } catch (e: Exception) {
                    errorFalhaStatus
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("$errorConectarServidor: ${e.message}"))
        }
    }
}