package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class RegistradoPor(
    @SerializedName("id") val id: String,
    @SerializedName("postoGraduacao") val postoGraduacao: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("matricula") val matricula: String
)