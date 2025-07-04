package com.example.e_ocorrencias.ui.screens.login

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sessionManager.getAccessToken()

        if (token == null) {
            Log.e("AuthInterceptor", "Token não encontrado!")
            return chain.proceed(originalRequest)
        }

        Log.d("AuthInterceptor", "Adicionando token ao header: Bearer ${token.take(10)}...")

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}