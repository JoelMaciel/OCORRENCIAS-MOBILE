package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class Endereco(
    @SerializedName("rua") val rua: String,
    @SerializedName("numero") val numero: String,
    @SerializedName("complemento") val complemento: String?,
    @SerializedName("bairro") val bairro: String,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("uf") val uf: String,
    @SerializedName("cep") val cep: String
)