package com.example.e_ocorrencias.data.repositories

import com.example.e_ocorrencias.data.remote.PoliciaService
import com.example.e_ocorrencias.data.models.response.page.PolicialResponse
import javax.inject.Inject

class PoliciaRepository @Inject constructor(
    private val policiaService: PoliciaService
) {
    suspend fun getPoliciais(page: Int): PolicialResponse {
        return try {
            policiaService.getPoliciais(page)
        } catch (e: Exception) {
            throw Exception("Falha na conexão")
        }
    }
}