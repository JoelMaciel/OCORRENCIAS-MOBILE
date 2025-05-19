package com.example.e_ocorrencias.ui.screens.ocorrencias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.components.OptionalField
import com.example.e_ocorrencias.ui.components.RequiredField
import com.example.e_ocorrencias.ui.theme.createButtonColors
import com.example.e_ocorrencias.ui.viewmodel.OcorrenciaViewModel
import com.example.e_ocorrencias.utils.getValue

@Composable
fun OcorrenciaEditScreen(
    navController: NavController,
    ocorrenciaId: String,
    viewModel: OcorrenciaViewModel = hiltViewModel()
) {
    val ocorrencia by viewModel.ocorrenciaDetails.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val mOcorrencia = remember { mutableStateOf("") }
    val dataHoraInicial = remember { mutableStateOf("") }
    val dataHoraFinal = remember { mutableStateOf("") }
    val tipoOcorrencia = remember { mutableStateOf("") }
    val artigo = remember { mutableStateOf("") }
    val resumo = remember { mutableStateOf("") }
    val rua = remember { mutableStateOf("") }
    val numero = remember { mutableStateOf("") }
    val complemento = remember { mutableStateOf("") }
    val bairro = remember { mutableStateOf("") }
    val cidade = remember { mutableStateOf("") }
    val uf = remember { mutableStateOf("") }
    val cep = remember { mutableStateOf("") }

    val showSuccessDialog = remember { mutableStateOf(false) }
    val (showError, setShowError) = remember { mutableStateOf(false) }
    val showStatusDialog = remember { mutableStateOf(false) }

    val showErrorDialog = remember { mutableStateOf(false) }
    val apiErrorMessage = remember { mutableStateOf("") }

    val showConcludedDialog = remember { mutableStateOf(false) }
    val showCanceledDialog = remember { mutableStateOf(false) }

    LaunchedEffect(ocorrenciaId) {
        viewModel.loadOcorrenciaDetails(ocorrenciaId)
    }

    LaunchedEffect(ocorrencia) {
        ocorrencia?.let {
            mOcorrencia.value = it.mOcorrencia
            dataHoraInicial.value = it.dataHoraInicial
            dataHoraFinal.value = it.dataHoraFinal
            tipoOcorrencia.value = it.tipoOcorrencia
            artigo.value = it.artigo
            resumo.value = it.resumo
            rua.value = it.endereco.rua
            numero.value = it.endereco.numero
            complemento.value = it.endereco.complemento ?: ""
            bairro.value = it.endereco.bairro
            cidade.value = it.endereco.cidade
            uf.value = it.endereco.uf
            cep.value = it.endereco.cep
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Voltar", fontSize = 16.sp)
            }
            Button(
                onClick = { showStatusDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Update,
                    contentDescription = "Atualizar Status",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Atualizar Status")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        RequiredField(
            value = mOcorrencia.value,
            onValueChange = { mOcorrencia.value = it },
            label = "M-Ocorrência",
            isError = showError && mOcorrencia.value.isEmpty(),
            errorMessage = "Campo M-Ocorrência é obrigatório"
        )

        RequiredField(
            value = dataHoraInicial.value,
            onValueChange = { dataHoraInicial.value = it },
            label = "Data Inicial",
            isError = showError && dataHoraInicial.value.isEmpty(),
            errorMessage = "Campo Data Inicial é obrigatório"
        )

        RequiredField(
            value = dataHoraFinal.value,
            onValueChange = { dataHoraFinal.value = it },
            label = "Data Final",
            isError = showError && dataHoraFinal.value.isEmpty(),
            errorMessage = "Campo Data Final é obrigatório"
        )

        RequiredField(
            value = tipoOcorrencia.value,
            onValueChange = { tipoOcorrencia.value = it },
            label = "Tipo de Ocorrência",
            isError = showError && tipoOcorrencia.value.isEmpty(),
            errorMessage = "Campo Tipo de Ocorrência é obrigatório"
        )

        RequiredField(
            value = artigo.value,
            onValueChange = { artigo.value = it },
            label = "Artigo",
            isError = showError && artigo.value.isEmpty(),
            errorMessage = "Campo Artigo é obrigatório"
        )

        RequiredField(
            value = resumo.value,
            onValueChange = { resumo.value = it },
            label = "Resumo",
            isError = showError && resumo.value.isEmpty(),
            errorMessage = "Campo Resumo é obrigatório"
        )

        Text(
            text = "Endereço:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        RequiredField(
            value = rua.value,
            onValueChange = { rua.value = it },
            label = "Rua",
            isError = showError && rua.value.isEmpty(),
            errorMessage = "Campo Rua é obrigatório"
        )

        RequiredField(
            value = numero.value,
            onValueChange = { numero.value = it },
            label = "Número",
            isError = showError && numero.value.isEmpty(),
            errorMessage = "Campo Número é obrigatório"
        )

        OptionalField(
            value = complemento.value,
            onValueChange = { complemento.value = it },
            label = "Complemento"
        )

        RequiredField(
            value = bairro.value,
            onValueChange = { bairro.value = it },
            label = "Bairro",
            isError = showError && bairro.value.isEmpty(),
            errorMessage = "Campo Bairro é obrigatório"
        )

        RequiredField(
            value = cidade.value,
            onValueChange = { cidade.value = it },
            label = "Cidade",
            isError = showError && cidade.value.isEmpty(),
            errorMessage = "Campo Cidade é obrigatório"
        )

        RequiredField(
            value = uf.value,
            onValueChange = { uf.value = it },
            label = "UF",
            isError = showError && uf.value.isEmpty(),
            errorMessage = "Campo UF é obrigatório"
        )

        RequiredField(
            value = cep.value,
            onValueChange = { cep.value = it },
            label = "CEP",
            isError = showError && cep.value.isEmpty(),
            errorMessage = "Campo CEP é obrigatório"
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                if (!isSaving) {
                    if (mOcorrencia.value.isNotEmpty() &&
                        dataHoraInicial.value.isNotEmpty() &&
                        dataHoraFinal.value.isNotEmpty() &&
                        tipoOcorrencia.value.isNotEmpty() &&
                        artigo.value.isNotEmpty() &&
                        resumo.value.isNotEmpty() &&
                        rua.value.isNotEmpty() &&
                        numero.value.isNotEmpty() &&
                        bairro.value.isNotEmpty() &&
                        cidade.value.isNotEmpty() &&
                        uf.value.isNotEmpty() &&
                        cep.value.isNotEmpty()
                    ) {
                        viewModel.updateOcorrencia(
                            id = ocorrenciaId,
                            mOcorrencia = mOcorrencia.value,
                            dataHoraInicial = dataHoraInicial.value,
                            dataHoraFinal = dataHoraFinal.value,
                            tipoOcorrencia = tipoOcorrencia.value,
                            artigo = artigo.value,
                            resumo = resumo.value,
                            rua = rua.value,
                            numero = numero.value,
                            complemento = complemento.value.ifEmpty { null },
                            bairro = bairro.value,
                            cidade = cidade.value,
                            uf = uf.value,
                            cep = cep.value
                        )
                        showSuccessDialog.value = true
                    } else {
                        setShowError(true)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f),
            colors = createButtonColors(),
            enabled = !isSaving
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
    }

    if (showSuccessDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog.value = false
                navController.navigate("ocorrencias") {
                    popUpTo("ocorrencias") { inclusive = true }
                }
            },
            title = {
                Text(
                    text = "Atualização Confirmada",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00C853)
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Sucesso",
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Os dados da ocorrência foram atualizados com sucesso!")
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            showSuccessDialog.value = false
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C853)
                        ),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }


    if (showErrorDialog.value) {
        AlertDialog(
            onDismissRequest = { showErrorDialog.value = false },
            title = {
                Text(
                    text = "Erro na Operação",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Erro",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = apiErrorMessage.value,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF616161)
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showErrorDialog.value = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.width(120.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text("Entendi")
                    }
                }
            }
        )
    }

    if (showStatusDialog.value) {
        AlertDialog(
            onDismissRequest = { showStatusDialog.value = false },
            title = {
                Text(
                    text = "Atualizar Status",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = { Text("Selecione a nova situação da ocorrência:") },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            viewModel.concludeOcorrencia(ocorrenciaId) { result ->
                                result.onSuccess {
                                    showConcludedDialog.value = true
                                }.onFailure { e ->
                                    apiErrorMessage.value =
                                        e.message ?: "Erro ao concluir ocorrência"
                                    showErrorDialog.value = true
                                }
                                showStatusDialog.value = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text("Concluída")
                    }

                    Button(
                        onClick = {
                            viewModel.canceledOcorrencia(ocorrenciaId) { result ->
                                result.onSuccess {
                                    showCanceledDialog.value = true
                                }.onFailure { e ->
                                    apiErrorMessage.value =
                                        e.message ?: "Erro ao cancelar ocorrência"
                                    showErrorDialog.value = true
                                }
                                showStatusDialog.value = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF44336)
                        )
                    ) {
                        Text("Cancelada")
                    }
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showStatusDialog.value = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9E9E9E),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .width(120.dp)
                            .height(49.dp)
                            .padding(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            "Fechar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

            }
        )
    }

    if (showConcludedDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showConcludedDialog.value = false
                viewModel.loadOcorrenciaDetails(ocorrenciaId)
            },
            title = {
                Text(
                    text = "Conclusão Confirmada",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            },
            text = {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Concluído",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Ocorrência concluída com sucesso!")
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            showConcludedDialog.value = false
                            viewModel.loadOcorrenciaDetails(ocorrenciaId)
                            navController.navigate("ocorrencia_details/$ocorrenciaId")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }

    if (showCanceledDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showCanceledDialog.value = false
                viewModel.loadOcorrenciaDetails(ocorrenciaId)
            },
            title = {
                Text(
                    text = "Cancelamento Confirmado",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF44336)
                )
            },
            text = {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancelado",
                        tint = Color(0xFFF44336),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Ocorrência cancelada com sucesso!")
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            showCanceledDialog.value = false
                            viewModel.loadOcorrenciaDetails(ocorrenciaId)
                            navController.navigate("ocorrencia_details/$ocorrenciaId")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF44336)
                        ),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }
}




