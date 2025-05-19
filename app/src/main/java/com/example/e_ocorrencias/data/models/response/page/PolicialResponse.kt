package com.example.e_ocorrencias.data.models.response.page

import com.example.e_ocorrencias.data.models.response.Policial
import com.google.gson.annotations.SerializedName

data class PolicialResponse(
    @SerializedName("policiais") val policiais: List<Policial>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int,
)