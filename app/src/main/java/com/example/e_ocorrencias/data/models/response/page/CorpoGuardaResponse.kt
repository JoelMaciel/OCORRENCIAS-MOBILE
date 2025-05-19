package com.example.e_ocorrencias.data.models.response.page

import com.example.e_ocorrencias.data.models.response.CorpoGuarda
import com.google.gson.annotations.SerializedName

data class CorpoGuardaResponse(
    @SerializedName("data") val corposGuarda: List<CorpoGuarda>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int
)