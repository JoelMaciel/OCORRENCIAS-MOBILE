package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.request.ViaturaRequest
import com.example.e_ocorrencias.data.models.response.Viatura
import com.example.e_ocorrencias.data.models.response.page.ViaturasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ViaturaService {
    @GET("viaturas")
    suspend fun getViaturas(@Query("page") page: Int): Response<ViaturasResponse>

    @GET("viaturas")
    suspend fun searchViaturas(
        @Query("prefixo") prefixo: String? = null,
        @Query("status") status: String? = null
    ): Response<ViaturasResponse>

    @GET("viaturas/{id}")
    suspend fun getViaturaById(@Path("id") id: String): Response<Viatura>

    @POST("viaturas")
    suspend fun createViatura(@Body viaturaRequest: ViaturaRequest): Response<Viatura>
}