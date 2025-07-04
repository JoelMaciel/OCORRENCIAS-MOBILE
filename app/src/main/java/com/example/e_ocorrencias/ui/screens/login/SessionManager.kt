package com.example.e_ocorrencias.ui.screens.login

import android.content.Context

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveAuthTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString("refresh_token", null)
    }

    fun clearTokens() {
        sharedPreferences.edit().apply {
            remove("access_token")
            remove("refresh_token")
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return getAccessToken() !=null
    }
}