package com.example.e_ocorrencias.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_ocorrencias.data.models.response.CorpoGuarda
import com.example.e_ocorrencias.data.repositories.CorpoGuardaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CorpoGuardaViewModel @Inject constructor(
    private val corpoGuardaRepository: CorpoGuardaRepository
) : ViewModel() {

    private val _corposGuarda = mutableStateOf<List<CorpoGuarda>>(emptyList())
    val corposGuarda: State<List<CorpoGuarda>> = _corposGuarda

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentPage = mutableStateOf(1)
    val currentPage: State<Int> = _currentPage

    private val _totalPages = mutableStateOf(0)
    val totalPages: State<Int> = _totalPages

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadCorpoGuarda() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true

            try {
                val response = corpoGuardaRepository.getCorposGuarda(_currentPage.value)
                _corposGuarda.value = _corposGuarda.value + response.corposGuarda
                _totalPages.value = response.totalPages
                _currentPage.value++
            } catch (e: Exception) {
                _error.value = "Erro ao carregar corpo da guarda: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun reset() {
        _corposGuarda.value = emptyList()
        _currentPage.value = 1
        _totalPages.value = 0
        loadCorpoGuarda()
    }
}