package com.example.e_ocorrencias.ui.screens.viaturas

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.example.e_ocorrencias.ui.viewmodel.ViaturaViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue
import kotlinx.coroutines.launch


@Composable
fun SearchViaturasScreen(
    navController: NavController,
    viewModel: ViaturaViewModel = hiltViewModel()
) {
    var prefixo by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var showSearchFields by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf(false) }

    val isSearch by viewModel.isSearching.collectAsStateWithLifecycle()
    val viaturas by viewModel.viaturas.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.cleanSearchResults()
    }

    LaunchedEffect(error) {
        error?.let { errorMessage ->
            scope.launch {
                viewModel.resetSearchError()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pesquisar Viaturas",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (showSearchFields) {
            OutlinedTextField(
                value = prefixo,
                onValueChange = { prefixo = it },
                label = { Text("Digite o prefixo da viatura") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Digite o status") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

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

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (prefixo.isBlank() && status.isBlank()) {
                            showError = true
                        } else {
                            showError = false
                            showSearchFields = false
                            viewModel.searchViaturas(
                                prefixo = prefixo.ifBlank { null },
                                status = status.ifBlank { null }
                            )
                        }
                    },
                    enabled = !isSearch,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853),
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    if (isSearch) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(text = "Buscar")
                    }
                }
            }

            if (showError) {
                Text(
                    text = "Preencha pelo menos um critério de busca",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }

        if (!showSearchFields) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    if (viaturas.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(viaturas) { index, viatura ->
                                ViaturaItem(
                                    viatura = viatura,
                                    navController = navController
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nenhum resultado encontrado",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        showSearchFields = true
                        viewModel.cleanSearchResults()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Nova Busca")
                }
            }
        }
    }
}