package com.example.e_ocorrencias.ui.screens.batalhoes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.example.e_ocorrencias.ui.theme.createButtonColors
import com.example.e_ocorrencias.ui.viewmodel.BatalhaoViewModel
import com.example.e_ocorrencias.utils.getValue


@Composable
fun BatalhaoEditScreen(
    navController: NavController,
    batalhaoId: String,
    viewModel: BatalhaoViewModel = hiltViewModel()
) {
    val batalhao by viewModel.batalhaoDetails.collectAsStateWithLifecycle()
    val nome = remember { mutableStateOf("") }
    val contato = remember { mutableStateOf("") }
    val rua = remember { mutableStateOf("") }
    val numero = remember { mutableStateOf("") }
    val bairro = remember { mutableStateOf("") }
    val cidade = remember { mutableStateOf("") }
    val complemento = remember { mutableStateOf("") }
    val uf = remember { mutableStateOf("") }
    val cep = remember { mutableStateOf("") }
    val showSuccessDialog = remember { mutableStateOf(false) }

    LaunchedEffect(batalhaoId) {
        viewModel.loadBatalhaoDetails(batalhaoId)
        viewModel.loadBatalhoes()
    }

    LaunchedEffect(batalhao) {
        batalhao?.let {
            nome.value = it.nome
            contato.value = it.contato

            val endereco = it.endereco
            rua.value = endereco.rua
            numero.value = endereco.numero
            bairro.value = endereco.bairro
            cidade.value = endereco.cidade
            complemento.value = endereco.complemento ?: ""
            uf.value = endereco.uf
            cep.value = endereco.cep
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TextButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Voltar", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Editar Batalhão",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            OutlinedTextField(
                value = nome.value,
                onValueChange = { nome.value = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contato.value,
                onValueChange = { contato.value = it },
                label = { Text("Contato") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text(
                text = "Endereço",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            OutlinedTextField(
                value = rua.value,
                onValueChange = { rua.value = it },
                label = { Text("Rua") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = numero.value,
                onValueChange = { numero.value = it },
                label = { Text("Número") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = complemento.value,
                onValueChange = { complemento.value = it },
                label = { Text("Complemento (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Fundos, AP 101") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bairro.value,
                onValueChange = { bairro.value = it },
                label = { Text("Bairro") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cidade.value,
                onValueChange = { cidade.value = it },
                label = { Text("Cidade") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uf.value,
                onValueChange = { uf.value = it },
                label = { Text("UF") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cep.value,
                onValueChange = { cep.value = it },
                label = { Text("CEP") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Button(
                onClick = {
                    viewModel.updateBatalhao(
                        id = batalhaoId,
                        nome = nome.value.ifEmpty { null },
                        contato = contato.value.ifEmpty { null },
                        rua = rua.value.ifEmpty { null },
                        numero = numero.value.ifEmpty { null },
                        complemento = complemento.value.ifEmpty { null },
                        bairro = bairro.value.ifEmpty { null },
                        cidade = cidade.value.ifEmpty { null },
                        uf = uf.value.ifEmpty { null },
                        cep = cep.value.ifEmpty { null },
                    )
                    showSuccessDialog.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = createButtonColors()
            ) {
                Text(text = "Salvar", color = Color.White, fontSize = 18.sp)
            }
        }
    }

    if (showSuccessDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog.value = false
                navController.navigate("batalhao_details/${batalhaoId}") {
                    popUpTo("batalhoes") {
                        inclusive = true
                        saveState = false
                    }
                    navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                }
            },
            title = {
                Text(
                    text = "Sucesso",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Os dados do batalhão foram atualizados com sucesso!",
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
                            showSuccessDialog.value = false
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))
                    ) {
                        Text("OK")
                    }
                }
            },
            dismissButton = {}
        )
    }
}
