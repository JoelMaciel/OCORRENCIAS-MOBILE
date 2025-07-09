package com.example.e_ocorrencias.ui.screens.login

import android.content.Context
import com.example.e_ocorrencias.data.models.response.PolicialInfo

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveAuthTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
    }

    fun saveUserData(policial: PolicialInfo) {
        sharedPreferences.edit().apply() {
            putString("user_id", policial.id)
            putString("user_nome", policial.nome)
            putString("user_matricula", policial.matricula)
            putString("user_roles", policial.roles.joinToString(","))
                .apply()
        }
    }

    fun getUserData(): PolicialInfo? {
        val id = sharedPreferences.getString("user_id", null)
        val nome = sharedPreferences.getString("user_nome", null)
        val matricula = sharedPreferences.getString("user_matricula", null)
        val roleString = sharedPreferences.getString("user_roles", null)

        val roles = if (roleString != null && roleString.isNotEmpty()) {
            roleString.split(",")
        } else {
            emptyList()
        }

        return if (id != null && nome != null && matricula != null) {
            PolicialInfo(
                id = id,
                nome = nome,
                matricula = matricula,
                roles = roles
            )
        } else {
            null
        }
    }

    fun getUserRoles(): List<String> {
        return sharedPreferences.getString("user_roles", "")?.split(",") ?: emptyList()
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
        return getAccessToken() != null
    }
}