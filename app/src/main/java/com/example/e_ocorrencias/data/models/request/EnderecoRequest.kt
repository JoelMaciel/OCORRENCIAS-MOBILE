package com.example.e_ocorrencias.data.models.request

data class EnderecoRequest(
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val complemento: String?,
    val uf: String,
    val cep: String
)