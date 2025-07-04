package com.example.e_ocorrencias.data.models.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val policial: PolicialInfo
)
