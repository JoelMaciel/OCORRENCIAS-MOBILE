package com.example.e_ocorrencias.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_ocorrencias.data.models.request.ViaturaRequest
import com.example.e_ocorrencias.data.models.response.Viatura
import com.example.e_ocorrencias.data.repositories.ViaturaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViaturaViewModel @Inject constructor(
    private val viaturaRepository: ViaturaRepository
) : ViewModel() {

    val errorCarregarDados = "Erro ao carregar dados da viatura"
    val errorSalvarViatura = "Erro ao salvar viatura"

    private val _viaturas = MutableStateFlow<List<Viatura>>(emptyList())
    val viaturas: StateFlow<List<Viatura>> = _viaturas.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(0)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched.asStateFlow()

    private val _selectedViatura = MutableStateFlow<Viatura?>(null)

    fun loadViaturas(refresh: Boolean = false) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch

            if (refresh) {
                _viaturas.value = emptyList()
                _currentPage.value = 1
                _totalPages.value = 0
            }
            _isLoading.value = true

            val result = viaturaRepository.getViaturas(_currentPage.value)
            result.onSuccess { response ->
                _viaturas.value = _viaturas.value + response.viaturas
                _totalPages.value = response.totalPages
                _currentPage.value++
            }.onFailure { exception ->
                _error.value = exception.message
            }
            _isLoading.value = false
        }
    }

    fun loadViaturaDetails(id: String) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            _error.value = null

            val result = viaturaRepository.getViaturaById(id)
            result.onSuccess { viatura ->
                _selectedViatura.value = viatura
            }.onFailure { exception ->
                _error.value = exception.message ?: errorCarregarDados
            }
            _isLoading.value = false
        }
    }

    fun createViatura(viaturaRequest: ViaturaRequest, refresh: Boolean = false) {
        viewModelScope.launch {
            if (_isSaving.value) return@launch
            _isSaving.value = true
            _error.value = null

            if (refresh) {
                reset()
            }

            val result = viaturaRepository.createViatura(viaturaRequest)
            result.onSuccess { viatura ->
                _viaturas.value = listOf(viatura) + _viaturas.value
                _isSuccess.value = true
            }.onFailure { exception ->
                _error.value = exception.message ?: errorSalvarViatura
            }
            _isSaving.value = false
        }
    }

    fun searchViaturas(
        prefixo: String? = null,
        status: String? = null
    ) {
        viewModelScope.launch {
            _isSearching.value = true
            _error.value = null

            val result = viaturaRepository.searchViaturas(
                prefixo = prefixo,
                status = status
            )
            result.onSuccess { response ->
                _viaturas.value = response.viaturas
                _hasSearched.value = true
            }.onFailure { exception ->
                _error.value = exception.message
            }
            _isSearching.value = false
        }
    }

    fun reset() {
        _viaturas.value = emptyList()
        _currentPage.value = 1
        _totalPages.value = 0
        loadViaturas()
    }

    fun cleanSearchResults() {
        _viaturas.value = emptyList()
        _error.value = null
    }

    fun resetSearchError() {
        _error.value = null
    }
}




















