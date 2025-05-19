package com.example.e_ocorrencias.data.models.request

data class OcorrenciaCreateRequest(
    val mOcorrencia: String,
    val dataHoraInicial: String,
    val dataHoraFinal: String?,
    val tipoOcorrencia: String,
    val artigo: String,
    val resumo: String,
    val guardaQuartelId: String,
    val registradoPorId: String,
    val policiaisEnvolvidos: List<String>,
    val delegaciaDestino: String,
    val delegadoResponsavel: String,
    val numeroProcedimento: String,
    val endereco: EnderecoRequest
)