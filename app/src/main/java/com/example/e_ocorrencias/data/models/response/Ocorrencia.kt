package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class Ocorrencia(
    @SerializedName("id") val id: String,
    @SerializedName("mOcorrencia") val mOcorrencia: String,
    @SerializedName("viatura") val viatura: String?,
    @SerializedName("policiaisEnvolvidos") val policiaisEnvolvidos: List<PolicialEnvolvido>,
    @SerializedName("dataHoraInicial") val dataHoraInicial: String,
    @SerializedName("dataHoraFinal") val dataHoraFinal: String?,
    @SerializedName("status") val status: String,
    @SerializedName("comandanteGuarda") val comandanteGuarda: String,
    @SerializedName("registradoPor") val registradoPor: RegistradoPor
)