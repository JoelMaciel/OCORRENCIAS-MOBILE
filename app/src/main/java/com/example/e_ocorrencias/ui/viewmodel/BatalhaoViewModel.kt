package com.example.e_ocorrencias.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_ocorrencias.data.models.request.BatalhaoRequest
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.data.repositories.BatalhaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatalhaoViewModel @Inject constructor(
    private val batalhaoRepository: BatalhaoRepository
) : ViewModel() {

    private val _batalhoes = MutableStateFlow<List<Batalhao>>(emptyList())
    val batalhoes: StateFlow<List<Batalhao>> = _batalhoes.asStateFlow()

    private val _batalhao = MutableStateFlow<Batalhao?>(null)
    val batalhao: StateFlow<Batalhao?> = _batalhao.asStateFlow()

    private val _refreshList = MutableStateFlow(false)
    val refreshList: StateFlow<Boolean> = _refreshList

    private val _batalhaoDetails = MutableStateFlow<Batalhao?>(null)
    val batalhaoDetails: StateFlow<Batalhao?> = _batalhaoDetails.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(0)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Batalhao>>(emptyList())
    val searchResults: StateFlow<List<Batalhao>> = _searchResults.asStateFlow()

    private val _searchError = MutableStateFlow<String?>(null)
    val searchError: StateFlow<String?> = _searchError.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _createBatalionSuccess = MutableStateFlow(false)
    val createBatalionSuccess: StateFlow<Boolean> = _createBatalionSuccess.asStateFlow()

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting.asStateFlow()

    private val _deleteError = MutableStateFlow<String?>(null)
    val deleteError: StateFlow<String?> = _deleteError.asStateFlow()

    fun loadBatalhoes(page: Int? = null) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true

            try {
                val targetPage = page ?: _currentPage.value
                val response = batalhaoRepository.getAllBatalhoes(targetPage)
                _batalhoes.value = if (targetPage == 1) {
                    response.batalhoes
                } else {
                    _batalhoes.value + response.batalhoes
                }

                _totalPages.value = response.totalPages
                _currentPage.value = targetPage + 1

            } catch (e: Exception) {
                _error.value = "Erro ao carregar batalhões: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createBatalhao(nome: String, contato: String, endereco: EnderecoRequest) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                val batalhaoRequest = BatalhaoRequest(
                    nome = nome,
                    contato = contato,
                    endereco = endereco
                )
                batalhaoRepository.createBatalhao(batalhaoRequest)
                refreshBatalhoes()
                _createBatalionSuccess.value = true
                reset()
            } catch (e: Exception) {
                _error.value = "Erro ao carregar batalhoes: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun loadBatalhaoDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                _batalhaoDetails.value = batalhaoRepository.getBatalhaoById(id)
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchBatalhoes(nome: String? = null) {
        viewModelScope.launch {
            _isSearching.value = true
            _searchError.value = null

            try {
                val response = batalhaoRepository.searchBatalhoes(nome)
                _searchResults.value = response.batalhoes
                _hasSearched.value = true
            } catch (e: Exception) {
                _searchError.value = e.message
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun deleteBatalhao(id: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            _isDeleting.value = true
            _deleteError.value = null

            try {
                val result = batalhaoRepository.deleteBatalhao(id)
                result.onSuccess {
                    if (batalhao.value?.id == id) {
                        _batalhao.value = null
                    }
                    onResult(Result.success(Unit))
                }.onFailure { throwable ->
                    _deleteError.value = throwable.message
                    onResult(Result.failure(throwable))
                }
            } catch (e: Exception) {
                _deleteError.value = e.message
                onResult(Result.failure(e))
            } finally {
                _isDeleting.value = false
            }
        }
    }

    fun updateBatalhao(
        id: String,
        nome: String? = null,
        contato: String? = null,
        rua: String? = null,
        numero: String? = null,
        complemento: String? = null,
        bairro: String? = null,
        cidade: String? = null,
        uf: String? = null,
        cep: String? = null
    ) {
        viewModelScope.launch {
            try {
                val updateBatalhao = batalhaoRepository.updateBatalhao(
                    id = id,
                    nome = nome,
                    contato = contato,
                    rua = rua,
                    numero = numero,
                    complemento = complemento,
                    bairro = bairro,
                    cidade = cidade,
                    uf = uf,
                    cep = cep
                )
                _batalhao.value = updateBatalhao
                triggerListRefresh()
            } catch (e: Exception) {
                _error.value = "Erro ao atualizar batalhão: ${e.message}"
            }
        }
    }

    fun refreshBatalhoes() {
        _currentPage.value = 1
        _batalhoes.value = emptyList()
        loadBatalhoes(page = 1)
    }

    fun triggerListRefresh() {
        _refreshList.value = true
    }

    fun reset() {
        _batalhoes.value = emptyList()
        _currentPage.value = 1
        _totalPages.value = 0
        _error.value = null
        loadBatalhoes()
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
        _hasSearched.value = false
        _isSearching.value = false
        _searchError.value = null
        refreshBatalhoes()
    }

    fun resetSearchError() {
        _searchError.value = null
    }

    fun clearDeleteError() {
        _deleteError.value = null
    }
}