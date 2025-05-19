package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.remote.CorpoGuardaService
import com.example.e_ocorrencias.data.models.response.page.CorpoGuardaResponse
import javax.inject.Inject

class CorpoGuardaRepository @Inject constructor(
    private val corpoGuardaService: CorpoGuardaService
) {
    suspend fun getCorposGuarda(page: Int): CorpoGuardaResponse {
        return try {
            corpoGuardaService.getCorposGuarda(page)
        } catch (e: Exception) {
            throw Exception("Falha na conexão: ${e.message}")
        }
    }
}