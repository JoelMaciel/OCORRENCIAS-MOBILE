package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class PolicialCorpoGuarda(
    @SerializedName("name") val nome: String,
    @SerializedName("graduacao") val graduacao: String
)
