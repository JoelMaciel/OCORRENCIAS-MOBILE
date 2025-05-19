package com.example.e_ocorrencias.data.models.response.page

import com.example.e_ocorrencias.data.models.response.Ocorrencia
import com.google.gson.annotations.SerializedName

data class OcorrenciasResponse(
    @SerializedName("data") val ocorrencias: List<Ocorrencia>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int
)