package com.example.e_ocorrencias.data.models.request

import kotlinx.serialization.Serializable

@Serializable
data class OcorrenciaUpdateRequest(
    val mOcorrencia: String,
    val dataHoraInicial: String,
    val dataHoraFinal: String,
    val tipoOcorrencia: String,
    val artigo: String,
    val resumo: String,
    val endereco: EnderecoRequest
)