package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class Viatura(
    @SerializedName("id") val id: String,
    @SerializedName("prefixo") val prefixo: String,
    @SerializedName("placa") val placa: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("status") val status: String,
    @SerializedName("batalhao") val batalhao: String
)
