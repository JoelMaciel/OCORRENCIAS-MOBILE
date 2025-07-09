package com.example.e_ocorrencias.ui.screens.batalhoes

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.e_ocorrencias.ui.viewmodel.BatalhaoViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatalhaoSearchScreen(
    navController: NavController,
    viewModel: BatalhaoViewModel = hiltViewModel(),
) {
    var nome by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }

    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchError by viewModel.error.collectAsStateWithLifecycle()
    val hasSearched by viewModel.hasSearched.collectAsStateWithLifecycle()
    val isForSelection = navController.currentBackStackEntry?.arguments?.getBoolean("isForSelection") ?: false


    LaunchedEffect(Unit) {
        viewModel.clearSearchResults()
    }

    LaunchedEffect(searchError) {
        searchError?.let { error ->
            viewModel.resetSearchError()
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Atenção") },
            text = { Text("Preencha o nome do batalhão") },
            confirmButton = {
                Button(
                    onClick = { showAlertDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (isForSelection) "Selecionar Batalhão" else "Buscar Batalhões",
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

        Spacer(modifier = Modifier.height(8.dp))

        if (!hasSearched || (hasSearched && searchResults.isEmpty())) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Digite o nome do batalhão") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.clearSearchResults()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 14.dp),
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
                        if (nome.isBlank()) {
                            showAlertDialog = true
                        } else {
                            viewModel.searchBatalhoes(
                                nome = nome.ifBlank { null },
                            )
                        }
                    },
                    enabled = !isSearching,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp),
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
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (searchResults.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(searchResults) { index, batalhao ->
                    if (isForSelection) {
                        Card(
                            onClick = {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "selectedBatalhao",
                                    batalhao
                                )
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = batalhao.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }else {
                        Column {
                            Text(text = "Resultado ${index + 1}")
                            BatalhaoItem(batalhao = batalhao, navController = navController)
                        }
                    }
                    Divider()
                }

                if (!isForSelection) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                nome = ""
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
        } else if (hasSearched && searchResults.isEmpty()) {
            Text(
                text = "Nenhum resultado encontrado.",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

