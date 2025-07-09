package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.request.BatalhaoRequest
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.data.models.response.page.BatalhaoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BatalhaoService {

    @GET("batalhoes")
    suspend fun getAllBatalhoes(@Query("page") page: Int): Response<BatalhaoResponse>

    @GET("batalhoes")
    suspend fun searchBatalhoes(@Query("nome") nome: String? = null): Response<BatalhaoResponse>

    @GET("batalhoes/{id}")
    suspend fun getBatalhaoById(@Path("id") id: String): Response<Batalhao>

    @POST("batalhoes")
    suspend fun createBatalhao(@Body batalhaoRequest: BatalhaoRequest): Response<Batalhao>

    @PUT("batalhoes/{id}")
    suspend fun updateBatalhao(
        @Path("id") id: String,
        @Body batalhaoRequest: BatalhaoRequest
    ): Response<Batalhao>

    @DELETE("batalhoes/{id}")
    suspend fun deleteBatalhao(@Path("id") id: String): Response<Unit>
}