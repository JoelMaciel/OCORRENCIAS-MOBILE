package com.example.e_ocorrencias.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.data.models.request.OcorrenciaCreateRequest
import com.example.e_ocorrencias.data.models.response.Ocorrencia
import com.example.e_ocorrencias.data.models.response.OcorrenciaDetails
import com.example.e_ocorrencias.data.repositories.OcorrenciaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OcorrenciaViewModel @Inject constructor(
    private val ocorrenciaRepository: OcorrenciaRepository
) : ViewModel() {

    private val _ocorrencias = MutableStateFlow<List<Ocorrencia>>(emptyList())
    val ocorrencias: StateFlow<List<Ocorrencia>> = _ocorrencias.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(0)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched.asStateFlow()

    private val _ocorrenciaDetails = MutableStateFlow<OcorrenciaDetails?>(null)
    val ocorrenciaDetails: StateFlow<OcorrenciaDetails?> = _ocorrenciaDetails.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    private val _refreshList = MutableStateFlow(false)
    val refreshList = _refreshList.asStateFlow()

    private val _isUpdatingStatus = MutableStateFlow(false)
    val isUpdatingStatus: StateFlow<Boolean> = _isUpdatingStatus.asStateFlow()


    fun loadOcorrencias() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true

            val result = ocorrenciaRepository.getOcorrencias(_currentPage.value)
            result.onSuccess { response ->
                _ocorrencias.value = _ocorrencias.value + response.ocorrencias
                _totalPages.value = response.totalPages
                _currentPage.value++
            }.onFailure { exception ->
                _error.value = exception.message
            }
            _isLoading.value = false
        }
    }

    fun loadOcorrenciaDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _ocorrenciaDetails.value = ocorrenciaRepository.getOcorrenciaById(id)
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro desconhecido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchOcorrencia(
        mOcorrencia: String? = null,
        prefixoViatura: String? = null,
        dataHoraInicial: String? = null,
        dataHoraFinal: String? = null,
        cidade: String? = null,
        bairro: String? = null
    ) {
        viewModelScope.launch {
            _isSearching.value = true
            _error.value = null

            try {
                val response = ocorrenciaRepository.searchOcorrencia(
                    mOcorrencia = mOcorrencia,
                    prefixoViatura = prefixoViatura,
                    dataHoraInicial = dataHoraInicial,
                    dataHoraFinal = dataHoraFinal,
                    cidade = cidade,
                    bairro = bairro
                )
                _ocorrencias.value = response.ocorrencias
                _hasSearched.value = true
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun updateOcorrencia(
        id: String,
        mOcorrencia: String,
        dataHoraInicial: String,
        dataHoraFinal: String,
        tipoOcorrencia: String,
        artigo: String,
        resumo: String,
        rua: String,
        numero: String,
        complemento: String?,
        bairro: String,
        cidade: String,
        uf: String,
        cep: String
    ) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                val updatedOcorrencia = ocorrenciaRepository.updateOcorrencia(
                    id = id,
                    mOcorrencia = mOcorrencia,
                    dataHoraInicial = dataHoraInicial,
                    dataHoraFinal = dataHoraFinal,
                    tipoOcorrencia = tipoOcorrencia,
                    artigo = artigo,
                    resumo = resumo,
                    rua = rua,
                    numero = numero,
                    complemento = complemento,
                    bairro = bairro,
                    cidade = cidade,
                    uf = uf,
                    cep = cep
                )
                _ocorrenciaDetails.value = updatedOcorrencia
            } catch (e: Exception) {
                _error.value = "Erro ao atualizar ocorrência: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun createOcorrencia(
        mOcorrencia: String,
        dataHoraInicial: String,
        dataHoraFinal: String?,
        tipoOcorrencia: String,
        artigo: String,
        resumo: String,
        guardaQuartelId: String,
        registradoPorId: String,
        policiaisEnvolvidos: List<String>,
        delegaciaDestino: String,
        delegadoResponsavel: String,
        numeroProcedimento: String,
        endereco: EnderecoRequest
    ) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                val ocorrenciaRequest = OcorrenciaCreateRequest(
                    mOcorrencia = mOcorrencia,
                    dataHoraInicial = dataHoraInicial,
                    dataHoraFinal = dataHoraFinal,
                    tipoOcorrencia = tipoOcorrencia,
                    artigo = artigo,
                    resumo = resumo,
                    guardaQuartelId = guardaQuartelId,
                    registradoPorId = registradoPorId,
                    policiaisEnvolvidos = policiaisEnvolvidos,
                    delegaciaDestino = delegaciaDestino,
                    delegadoResponsavel = delegadoResponsavel,
                    numeroProcedimento = numeroProcedimento,
                    endereco = endereco
                )
                ocorrenciaRepository.createOcorrencia(ocorrenciaRequest)
                _isSuccess.value = true
            } catch (e: Exception) {
                _error.value = "Erro ao carregar ocorrências: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun concludeOcorrencia(id: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            _isUpdatingStatus.value = true
            _error.value = null
            try {
                val result = ocorrenciaRepository.statusConcluded(id)
                result.onSuccess {
                    onResult(Result.success(Unit))
                }.onFailure { throwable ->
                    val errorMsg = throwable.message ?: "Erro ao atualizar status"
                    onResult(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                onResult(Result.failure(e))
            } finally {
                _isUpdatingStatus.value = false
            }
        }
    }

    fun canceledOcorrencia(id: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            _isUpdatingStatus.value = true
            _error.value = null

            try {
                val result = ocorrenciaRepository.statusCanceled(id)
                result.onSuccess {
                    onResult(Result.success(Unit))
                }.onFailure { throwable ->
                    _error.value = throwable.message
                    onResult(Result.failure(throwable))
                }
            } catch (e: Exception) {
                _error.value = e.message
                onResult(Result.failure(e))
            } finally {
                _isUpdatingStatus.value = false
            }
        }
    }

    fun reset() {
        _ocorrencias.value = emptyList()
        _currentPage.value = 1
        _totalPages.value = 0
        loadOcorrencias()
    }

    fun clearSearchResults() {
        _ocorrencias.value = emptyList()
        _error.value = null
    }

    fun resetSearchError() {
        _error.value = null
    }

    fun setRefreshList(value: Boolean) {
        _refreshList.value = value
    }

}

