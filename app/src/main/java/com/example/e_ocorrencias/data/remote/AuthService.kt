package com.example.e_ocorrencias.data.remote

import com.example.e_ocorrencias.data.models.request.LoginRequest
import com.example.e_ocorrencias.data.models.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>
}