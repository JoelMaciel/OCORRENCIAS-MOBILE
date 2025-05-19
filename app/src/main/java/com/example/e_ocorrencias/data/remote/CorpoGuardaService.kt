package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.response.page.CorpoGuardaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CorpoGuardaService {
    @GET("corpoGuarda")
    suspend fun getCorposGuarda(@Query("page") page: Int): CorpoGuardaResponse
}