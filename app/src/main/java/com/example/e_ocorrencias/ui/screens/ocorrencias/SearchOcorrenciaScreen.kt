package com.example.e_ocorrencias.ui.screens.ocorrencias

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.viewmodel.OcorrenciaViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcorrenciaSearchScreen(
    navController: NavController,
    viewModel: OcorrenciaViewModel = hiltViewModel()
) {
    var mOcorrencia by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var prefixoViatura by remember { mutableStateOf("") }
    var dataHoraInicial by remember { mutableStateOf("") }
    var dataHoraFinal by remember { mutableStateOf("") }

    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val ocorrencias by viewModel.ocorrencias.collectAsStateWithLifecycle()
    val searchError by viewModel.error.collectAsStateWithLifecycle()
    var showForm by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.clearSearchResults()
    }

    LaunchedEffect(searchError) {
        searchError?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(error)
                viewModel.resetSearchError()
            }
        }
    }

    Scaffold(
        snackbarHost = { CustomSnackbar(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Buscar Ocorrências",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (showForm) {
                OutlinedTextField(
                    value = mOcorrencia,
                    onValueChange = { mOcorrencia = it },
                    label = { Text("M-Ocorrência") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bairro,
                    onValueChange = { bairro = it },
                    label = { Text("Bairro") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = prefixoViatura,
                    onValueChange = { prefixoViatura = it },
                    label = { Text("Prefixo da Viatura") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dataHoraInicial,
                    onValueChange = { dataHoraInicial = it },
                    label = { Text("Data/Hora Inicial (DD/MM/YYYY HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dataHoraFinal,
                    onValueChange = { dataHoraFinal = it },
                    label = { Text("Data/Hora Final (DD/MM/YYYY HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Voltar")
                    }

                    Button(
                        onClick = {
                            if (mOcorrencia.isBlank() &&
                                prefixoViatura.isBlank() &&
                                dataHoraInicial.isBlank() &&
                                dataHoraFinal.isBlank() &&
                                cidade.isBlank() &&
                                bairro.isBlank()
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Preencha pelo menos um critério de busca")
                                }
                            } else {
                                showForm = false
                                viewModel.searchOcorrencia(
                                    mOcorrencia = mOcorrencia.ifBlank { null },
                                    prefixoViatura = prefixoViatura.ifBlank { null },
                                    dataHoraInicial = dataHoraInicial.ifBlank { null },
                                    dataHoraFinal = dataHoraFinal.ifBlank { null },
                                    cidade = cidade.ifBlank { null },
                                    bairro = bairro.ifBlank { null },
                                )
                            }
                        },
                        enabled = !isSearching,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C853),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        if (isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(text = "Buscar")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (ocorrencias.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(ocorrencias) { index, ocorrencia ->
                        Column {
                            Text(
                                text = "Resultado ${index + 1}",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            OcorrenciaItem(
                                ocorrencia = ocorrencia,
                                navController = navController
                            )
                            Divider()
                        }
                    }
                }
            } else if (!showForm && ocorrencias.isEmpty()) {
                Text(
                    text = "Nenhum resultado encontrado.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (!showForm) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        showForm = true
                        viewModel.clearSearchResults()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Voltar para a busca")
                }
            }
        }
    }
}


