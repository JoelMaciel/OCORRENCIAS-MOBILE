package com.example.e_ocorrencias.data.models.request

data class BatalhaoRequest(
    val nome: String,
    val contato: String,
    val endereco: EnderecoRequest
)