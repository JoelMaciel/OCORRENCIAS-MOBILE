package com.example.e_ocorrencias.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_ocorrencias.data.models.request.LoginRequest
import com.example.e_ocorrencias.data.models.response.PolicialInfo
import com.example.e_ocorrencias.data.remote.AuthService
import com.example.e_ocorrencias.ui.screens.login.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    sealed class AuthResult {
        object Initial : AuthResult()
        object Loading : AuthResult()
        data class Success(val user: PolicialInfo) : AuthResult()
        data class Error(val message: String, val code: Int? = null) : AuthResult()
    }

    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Initial)
    val authResult: StateFlow<AuthResult> = _authResult

    fun isUserLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    suspend fun login(email: String, password: String) {
        _authResult.value = AuthResult.Loading
        try {
            val response = authService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    sessionManager.saveAuthTokens(
                        loginResponse.accessToken,
                        loginResponse.refreshToken
                    )
                    _authResult.value = AuthResult.Success(loginResponse.policial)
                } ?: run {
                    _authResult.value = AuthResult.Error("Resposta inválida do servidor")
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Credenciais inválidas"
                    400 -> "Requisição inválida"
                    500 -> "Erro interno do servidor"
                    else -> "Erro desconhecido"
                }
                _authResult.value = AuthResult.Error(errorMessage, response.code())
            }
        } catch (e: Exception) {
            _authResult.value = AuthResult.Error("Error de conexão: ${e.message}")
        }
    }

    fun logout() {
        sessionManager.clearTokens()
    }

    fun getCurrentUser(): PolicialInfo? {
        return null
    }
}
