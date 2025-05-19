package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class Batalhao(
    @SerializedName("id") val id: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("contato") val contato: String,
    @SerializedName("dataCriacao") val dataCriacao: String,
    @SerializedName("dataAtualizacao") val dataAtualizacao: String,
    @SerializedName("endereco") val endereco: Endereco
)