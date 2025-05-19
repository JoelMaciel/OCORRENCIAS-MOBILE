package com.example.e_ocorrencias.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_ocorrencias.data.models.response.Policial
import com.example.e_ocorrencias.data.repositories.PoliciaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoliciaViewModel @Inject constructor(
    private val policiaRepository: PoliciaRepository
) : ViewModel() {

    private val _policiais = mutableStateOf<List<Policial>>(emptyList())
    val policiais: State<List<Policial>> = _policiais

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentPage = mutableStateOf(1)
    val currentPage: State<Int> = _currentPage

    private val _totalPages = mutableStateOf(0)
    val totalPages: State<Int> = _totalPages

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadPoliciais() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true

            try {
                val response = policiaRepository.getPoliciais(_currentPage.value)
                _policiais.value = _policiais.value + response.policiais
                _totalPages.value = response.totalPages
                _currentPage.value++
            } catch (e: Exception) {
                _error.value = "Erro ao carregar policiais: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun reset() {
        _policiais.value = emptyList()
        _currentPage.value = 1
        _totalPages.value = 0
        loadPoliciais()
    }
}