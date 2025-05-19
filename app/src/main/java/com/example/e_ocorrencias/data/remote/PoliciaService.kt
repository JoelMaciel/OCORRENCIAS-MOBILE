package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.response.page.PolicialResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PoliciaService {
    @GET("policiais")
    suspend fun getPoliciais(@Query("page") page: Int): PolicialResponse
}