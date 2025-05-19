package com.example.e_ocorrencias.data.models.response.page

import com.example.e_ocorrencias.data.models.response.Batalhao
import com.google.gson.annotations.SerializedName

data class BatalhaoResponse(
    @SerializedName("batalhoes") val batalhoes: List<Batalhao>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("totalPages") val totalPages: Int
)