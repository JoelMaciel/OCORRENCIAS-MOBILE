package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class OcorrenciaDetails(
    @SerializedName("id") val id: String,
    @SerializedName("mOcorrencia") val mOcorrencia: String,
    @SerializedName("viatura") val viatura: String?,
    @SerializedName("policiaisEnvolvidos") val policiaisEnvolvidos: List<PolicialEnvolvido>,
    @SerializedName("dataHoraInicial") val dataHoraInicial: String,
    @SerializedName("dataHoraFinal") val dataHoraFinal: String,
    @SerializedName("tipoOcorrencia") val tipoOcorrencia: String,
    @SerializedName("artigo") val artigo: String,
    @SerializedName("resumo") val resumo: String,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("delegaciaDestino") val delegaciaDestino: String,
    @SerializedName("delegadoResponsavel") val delegadoResponsavel: String,
    @SerializedName("numeroProcedimento") val numeroProcedimento: String,
    @SerializedName("comandanteGuarda") val comandanteGuarda: String,
    @SerializedName("registradoPor") val registradoPor: RegistradoPor,
    @SerializedName("fiscal") val fiscal: Fiscal,
    @SerializedName("supervisor") val supervisor: Supervisor,
    @SerializedName("endereco") val endereco: Endereco
)