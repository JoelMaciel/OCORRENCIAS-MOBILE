package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.request.OcorrenciaCreateRequest
import com.example.e_ocorrencias.data.models.request.OcorrenciaUpdateRequest
import com.example.e_ocorrencias.data.models.response.OcorrenciaDetails
import com.example.e_ocorrencias.data.models.response.page.OcorrenciasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OcorrenciaService {
    @GET("ocorrencias")
    suspend fun getOcorrencias(@Query("page") page: Int): Response<OcorrenciasResponse>

    @GET("ocorrencias")
    suspend fun searchOcorrencia(
        @Query("mOcorrencia") mOcorrencia: String? = null,
        @Query("prefixoViatura") prefixoViatura: String? = null,
        @Query("dataHoraInicial") dataHoraInicial: String? = null,
        @Query("dataHoraFinal") dataHoraFinal: String? = null,
        @Query("cidade") cidade: String? = null,
        @Query("bairro") bairro: String? = null,
        @Query("limit") limit: Int? = null
    ): OcorrenciasResponse

    @GET("ocorrencias/{id}")
    suspend fun getOcorrenciaById(@Path("id") id: String): OcorrenciaDetails

    @PATCH("ocorrencias/{id}")
    suspend fun updateOcorrencia(
        @Path("id") id: String, @Body updateRequest: OcorrenciaUpdateRequest
    ): OcorrenciaDetails

    @POST("ocorrencias")
    suspend fun createOcorrencia(@Body ocorrenciaRequest: OcorrenciaCreateRequest): OcorrenciaDetails

    @PATCH("ocorrencias/{id}/concluir")
    suspend fun statusConcluded(@Path("id") id: String): Response<Unit>

    @PATCH("ocorrencias/{id}/cancelar")
    suspend fun statusCanceled(@Path("id") id: String): Response<Unit>

}