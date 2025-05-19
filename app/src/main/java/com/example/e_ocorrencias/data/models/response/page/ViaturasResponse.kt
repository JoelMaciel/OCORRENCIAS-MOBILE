package com.example.e_ocorrencias.data.models.response.page

import com.example.e_ocorrencias.data.models.response.Viatura
import com.google.gson.annotations.SerializedName

data class ViaturasResponse(
    @SerializedName("viaturas") val viaturas: List<Viatura>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int
)