package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class CorpoGuarda(
    @SerializedName("id") val id: String,
    @SerializedName("batalhao") val batalhao: String,
    @SerializedName("comandante") val comandante: String?,
    @SerializedName("postoGraduacao") val postoGraduacao: String,
    @SerializedName("policiais") val policiais: List<PolicialCorpoGuarda>,
    @SerializedName("dataCriacao") val dataCriacao: String?,
    @SerializedName("dataAtualizacao") val dataAtualizacao: String,
)