package com.example.e_ocorrencias.ui.screens.batalhoes

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.viewmodel.BatalhaoViewModel
import com.example.e_ocorrencias.utils.DateUtils
import com.example.e_ocorrencias.utils.getValue


@Composable
fun BatalhaoDetailsScreen(
    navController: NavController,
    batalhaoId: String,
    viewModel: BatalhaoViewModel = hiltViewModel()
) {
    val batalhao by viewModel.batalhaoDetails.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isDeleting by viewModel.isDeleting.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val showDeleteDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current


    LaunchedEffect(batalhaoId) {
        viewModel.loadBatalhaoDetails(batalhaoId)
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
                text = "Detalhes do Batalhão",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                },
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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate("batalhao_edit/${batalhaoId}")
                },
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Atualizar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(text = "Atualizar")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8E8E8)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                } else if (error != null) {
                    Text(text = "Error: ${error}", color = Color.Red)
                } else {
                    batalhao?.let { batalhao ->
                        Text(
                            text = "Nome: ${batalhao.nome}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Contato: ${batalhao.contato}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Endereço",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rua: ${batalhao.endereco.rua}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Número: ${batalhao.endereco.numero}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        batalhao.endereco.complemento?.let {
                            Text(
                                text = "Complemento: $it",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        Text(
                            text = "Bairro: ${batalhao.endereco.bairro}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Cidade: ${batalhao.endereco.cidade}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "UF: ${batalhao.endereco.uf}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "CEP: ${batalhao.endereco.cep}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Data de Criação: ${DateUtils.formatDate(batalhao.dataCriacao)}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Última Atualização: ${DateUtils.formatDate(batalhao.dataAtualizacao)}",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        Button(
            onClick = { showDeleteDialog.value = true },
            modifier = Modifier
                .width(150.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6F61),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(50.dp),
            enabled = !isLoading && !isDeleting && error == null
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Deletar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Deletar")
                if (isDeleting) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }
            }
        }

        if (showDeleteDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog.value = false
                    viewModel.clearDeleteError()
                },
                title = {
                    Text(
                        "Confirmar exclusão",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        "Tem certeza que deseja deletar este batalhão?",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                viewModel.deleteBatalhao(batalhaoId) { result ->
                                    result.onSuccess {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "refresh",
                                            true
                                        )
                                        navController.popBackStack()
                                    }.onFailure { throwable ->
                                        Toast.makeText(
                                            context,
                                            throwable.message ?: "Erro desconhecido",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                showDeleteDialog.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Sim")
                        }
                        Button(
                            onClick = {
                                showDeleteDialog.value = false
                                viewModel.clearDeleteError()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Cancelar")
                        }
                    }
                },
                dismissButton = {}
            )
        }
    }
}