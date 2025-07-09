package com.example.e_ocorrencias.ui.screens.ocorrencias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.ui.components.RequiredField
import com.example.e_ocorrencias.ui.viewmodel.OcorrenciaViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue


@Composable
fun OcorrenciaCreateScreen(
    navController: NavController,
    viewModel: OcorrenciaViewModel = hiltViewModel()
) {

    var mOcorrencia by remember { mutableStateOf("") }
    var dataHoraInicial by remember { mutableStateOf("") }
    var dataHoraFinal by remember { mutableStateOf("") }
    var tipoOcorrencia by remember { mutableStateOf("") }
    var artigo by remember { mutableStateOf("") }
    var resumo by remember { mutableStateOf("") }
    var guardaQuartelId by remember { mutableStateOf("") }
    var registradoPorId by remember { mutableStateOf("") }
    var policiaisEnvolvidos by remember { mutableStateOf("") }
    var delegaciaDestino by remember { mutableStateOf("") }
    var delegadoResponsavel by remember { mutableStateOf("") }
    var numeroProcedimento by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }

    val (showError, setShowError) = remember { mutableStateOf(false) }
    val (mOcorrenciaError, setMOcorrenciaError) = remember { mutableStateOf(false) }
    val (dataHoraInicialError, setDataHoraInicialError) = remember { mutableStateOf(false) }
    val (dataHoraFinalError, setDataHoraFinalError) = remember { mutableStateOf(false) }
    val (tipoOcorrenciaError, setTipoOcorrenciaError) = remember { mutableStateOf(false) }
    val (artigoError, setArtigoError) = remember { mutableStateOf(false) }
    val (resumoError, setResumoError) = remember { mutableStateOf(false) }
    val (guardaQuartelIdError, setGuardaQuartelIdError) = remember { mutableStateOf(false) }
    val (registradoPorIdError, setRegistradoPorIdError) = remember { mutableStateOf(false) }
    val (policiaisEnvolvidosError, setPoliciaisEnvolvidosError) = remember { mutableStateOf(false) }
    val (delegaciaDestinoError, setDelegaciaDestinoError) = remember { mutableStateOf(false) }
    val (delegadoResponsavelError, setDelegadoResponsavelError) = remember { mutableStateOf(false) }
    val (numeroProcedimentoError, setNumeroProcedimentoError) = remember { mutableStateOf(false) }
    val (ruaError, setRuaError) = remember { mutableStateOf(false) }
    val (numeroEnderecoError, setNumeroEnderecoError) = remember { mutableStateOf(false) }
    val (bairroError, setBairroError) = remember { mutableStateOf(false) }
    val (cidadeError, setCidadeError) = remember { mutableStateOf(false) }
    val (ufError, setUfError) = remember { mutableStateOf(false) }
    val (cepError, setCepError) = remember { mutableStateOf(false) }

    val isSaving by viewModel.isSaving.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val (showSuccessDialog, setShowSuccessDialog) = remember { mutableStateOf(false) }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            setShowSuccessDialog(true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cadastrar Ocorrência",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 20.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                RequiredField(
                    value = mOcorrencia,
                    onValueChange = { mOcorrencia = it; setMOcorrenciaError(false) },
                    label = "M-Ocorrência",
                    isError = mOcorrenciaError,
                    errorMessage = "O campo M-Ocorrência  é obrigatório"
                )

                RequiredField(
                    value = dataHoraInicial,
                    onValueChange = { dataHoraInicial = it; setDataHoraInicialError(false) },
                    label = "Data/Hora Inicial (YYYY-MM-DDTHH:mm)",
                    isError = dataHoraInicialError,
                    errorMessage = "O campo Data/Hora Inicial  é obrigatório"
                )

                RequiredField(
                    value = dataHoraFinal,
                    onValueChange = { dataHoraFinal = it; setDataHoraFinalError(false) },
                    label = "Data/Hora Final (YYYY-MM-DDTHH:mm)",
                    isError = dataHoraFinalError,
                    errorMessage = "O campo Data/Hora Final é obrigatório"
                )

                RequiredField(
                    value = tipoOcorrencia,
                    onValueChange = { tipoOcorrencia = it; setTipoOcorrenciaError(false) },
                    label = "Tipo de Ocorrência",
                    isError = tipoOcorrenciaError,
                    errorMessage = "O campo Tipo de Ocorrência é obrigatório"
                )

                RequiredField(
                    value = artigo,
                    onValueChange = { artigo = it; setArtigoError(false) },
                    label = "Artigo",
                    isError = artigoError,
                    errorMessage = "O campo Artigo é obrigatório"
                )

                RequiredField(
                    value = resumo,
                    onValueChange = { resumo = it; setResumoError(false) },
                    label = "Resumo",
                    isError = resumoError,
                    errorMessage = "O campo Resumo é obrigatório"
                )

                RequiredField(
                    value = guardaQuartelId,
                    onValueChange = { guardaQuartelId = it; setGuardaQuartelIdError(false) },
                    label = "ID do Quartel",
                    isError = guardaQuartelIdError,
                    errorMessage = "O campo ID do Quartel é obrigatório"
                )

                RequiredField(
                    value = registradoPorId,
                    onValueChange = { registradoPorId = it; setRegistradoPorIdError(false) },
                    label = "ID do Policial Registrador",
                    isError = registradoPorIdError,
                    errorMessage = "O campo ID do Policial Registrador é obrigatório"
                )

                RequiredField(
                    value = policiaisEnvolvidos,
                    onValueChange = {
                        policiaisEnvolvidos = it; setPoliciaisEnvolvidosError(false)
                    },
                    label = "IDs dos Policiais Envolvidos (separados por vírgula)",
                    isError = policiaisEnvolvidosError,
                    errorMessage = "O campo IDs dos Policiais é obrigatório"
                )

                RequiredField(
                    value = delegaciaDestino,
                    onValueChange = { delegaciaDestino = it; setDelegaciaDestinoError(false) },
                    label = "Delegacia Destino",
                    isError = delegaciaDestinoError,
                    errorMessage = "O campo Delegacia Destino é obrigatório"
                )

                RequiredField(
                    value = delegadoResponsavel,
                    onValueChange = {
                        delegadoResponsavel = it; setDelegadoResponsavelError(false)
                    },
                    label = "Delegado Responsável",
                    isError = delegadoResponsavelError,
                    errorMessage = "O campo Delegado Responsável é obrigatório"
                )

                RequiredField(
                    value = numeroProcedimento,
                    onValueChange = { numeroProcedimento = it; setNumeroProcedimentoError(false) },
                    label = "Número do Procedimento",
                    isError = numeroProcedimentoError,
                    errorMessage = "O campo Número do Procedimento é obrigatório"
                )

                Text(
                    text = "Endereço",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                RequiredField(
                    value = rua,
                    onValueChange = { rua = it; setRuaError(false) },
                    label = "Rua",
                    isError = ruaError,
                    errorMessage = "O campo Rua é obrigatório"
                )

                RequiredField(
                    value = numero,
                    onValueChange = { numero = it; setNumeroEnderecoError(false) },
                    label = "Número",
                    isError = numeroEnderecoError,
                    errorMessage = "O campo Número é obrigatório"
                )

                RequiredField(
                    value = bairro,
                    onValueChange = { bairro = it; setBairroError(false) },
                    label = "Bairro",
                    isError = bairroError,
                    errorMessage = "O campo Bairro é obrigatório"
                )

                RequiredField(
                    value = cidade,
                    onValueChange = { cidade = it; setCidadeError(false) },
                    label = "Cidade",
                    isError = cidadeError,
                    errorMessage = "O campo Cidade é obrigatório"
                )

                RequiredField(
                    value = complemento,
                    onValueChange = { complemento = it },
                    label = "Complemento",
                    isError = false,
                    errorMessage = "O campo Complemento é obrigatório"
                )

                RequiredField(
                    value = uf,
                    onValueChange = { uf = it; setUfError(false) },
                    label = "UF",
                    isError = ufError,
                    errorMessage = "O campo UF é obrigatório"
                )

                RequiredField(
                    value = cep,
                    onValueChange = { cep = it; setCepError(false) },
                    label = "CEP",
                    isError = cepError,
                    errorMessage = "O campo CEP é obrigatório"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val requiredFields: List<Pair<String, (Boolean) -> Unit>> = listOf(
                            mOcorrencia to { setMOcorrenciaError(it) },
                            dataHoraInicial to { setDataHoraInicialError(it) },
                            dataHoraFinal to { setDataHoraFinalError(it) },
                            tipoOcorrencia to { setTipoOcorrenciaError(it) },
                            artigo to { setArtigoError(it) },
                            resumo to { setResumoError(it) },
                            guardaQuartelId to { setGuardaQuartelIdError(it) },
                            registradoPorId to { setRegistradoPorIdError(it) },
                            policiaisEnvolvidos to { setPoliciaisEnvolvidosError(it) },
                            delegaciaDestino to { setDelegaciaDestinoError(it) },
                            delegadoResponsavel to { setDelegadoResponsavelError(it) },
                            numeroProcedimento to { setNumeroProcedimentoError(it) },
                            rua to { setRuaError(it) },
                            numero to { setNumeroEnderecoError(it) },
                            bairro to { setBairroError(it) },
                            cidade to { setCidadeError(it) },
                            uf to { setUfError(it) },
                            cep to { setCepError(it) }
                        )

                        val invalidFields = requiredFields.filter { it.first.isBlank() }

                        if (invalidFields.isNotEmpty()) {
                            invalidFields.forEach { it.second(true) }
                            setShowError(true)
                        } else {
                            setShowError(false)
                            viewModel.createOcorrencia(
                                mOcorrencia = mOcorrencia,
                                dataHoraInicial = dataHoraInicial,
                                dataHoraFinal = dataHoraFinal.ifBlank { null },
                                tipoOcorrencia = tipoOcorrencia,
                                artigo = artigo,
                                resumo = resumo,
                                guardaQuartelId = guardaQuartelId,
                                registradoPorId = registradoPorId,
                                policiaisEnvolvidos = policiaisEnvolvidos.split(",")
                                    .map { it.trim() }
                                    .filter { it.isNotBlank() },
                                delegaciaDestino = delegaciaDestino,
                                delegadoResponsavel = delegadoResponsavel,
                                numeroProcedimento = numeroProcedimento,
                                endereco = EnderecoRequest(
                                    rua = rua,
                                    numero = numero,
                                    bairro = bairro,
                                    cidade = cidade,
                                    complemento = complemento.takeIf { it.isNotBlank() },
                                    uf = uf,
                                    cep = cep
                                )
                            )
                        }
                    },
                    enabled = !isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                        .padding(top = 7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(text = "Salvar")
                    }
                }

                if (showError) {
                    Text(
                        text = "Preencha todos os campos obrigatórios",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowSuccessDialog(false)
                navController.popBackStack()
            },
            title = {
                Text(
                    text = "Sucesso",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text("Ocorrência cadastrada com sucesso!")
            },
            confirmButton = {
                Button(
                    onClick = {
                        setShowSuccessDialog(false)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853),
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            }
        )
    }
}

