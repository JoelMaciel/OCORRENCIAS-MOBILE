package com.example.e_ocorrencias.data.models.request

data class ViaturaRequest(
    val prefixo: String,
    val placa: String,
    val modelo: String,
    val batalhaoId: String
)
