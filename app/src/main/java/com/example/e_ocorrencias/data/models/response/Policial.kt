package com.example.e_ocorrencias.data.models.response

import com.google.gson.annotations.SerializedName

data class Policial(
    @SerializedName("id") val id: String ,
    @SerializedName("nome") val nome: String ,
    @SerializedName("matricula") val matricula: String ,
    @SerializedName("postoGraduacao") val postoGraduacao: String ,
    @SerializedName("cpf") val cpf: String ,
    @SerializedName("contato") val contato: String ,
    @SerializedName("email") val email: String ,
    @SerializedName("batalhao") val batalhao: String ,
    @SerializedName("createdAt") val createdAt: String ,
    @SerializedName("updatedAt") val updatedAt: String ,

)
