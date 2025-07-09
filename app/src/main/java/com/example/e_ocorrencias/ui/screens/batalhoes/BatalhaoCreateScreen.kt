package com.example.e_ocorrencias.ui.screens.batalhoes

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.e_ocorrencias.data.models.request.EnderecoRequest
import com.example.e_ocorrencias.ui.components.RequiredField
import com.example.e_ocorrencias.ui.viewmodel.BatalhaoViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue

@Composable
fun BatalhaoCreateScreen(
    navController: NavController,
    viewModel: BatalhaoViewModel = hiltViewModel()
) {
    var nome by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }

    val (showError, setShowError) = remember { mutableStateOf(false) }
    val (nomeError, setNomeError) = remember { mutableStateOf(false) }
    val (contatoError, setContatoError) = remember { mutableStateOf(false) }
    val (ruaError, setRuaError) = remember { mutableStateOf(false) }
    val (numeroEnderecoError, setNumeroEnderecoError) = remember { mutableStateOf(false) }
    val (bairroError, setBairroError) = remember { mutableStateOf(false) }
    val (cidadeError, setCidadeError) = remember { mutableStateOf(false) }
    val (ufError, setUfError) = remember { mutableStateOf(false) }
    val (cepError, setCepError) = remember { mutableStateOf(false) }

    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val createBatalionSuccess by viewModel.isSaving.collectAsState()
    val (showSuccessDialog, setShowSuccessDialog) = remember { mutableStateOf(false) }

    LaunchedEffect(createBatalionSuccess) {
        if (createBatalionSuccess) {
            setShowSuccessDialog(true)
            navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Cadastrar Batalhão",
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

        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                RequiredField(
                    value = nome,
                    onValueChange = { nome = it; setNomeError(false) },
                    label = "Nome",
                    isError = nomeError,
                    errorMessage = "O campo nome é obrigatório"
                )

                RequiredField(
                    value = contato,
                    onValueChange = {contato = it;setContatoError(false)},
                    label = "Contato",
                    isError = contatoError,
                    errorMessage = "O campo contato é obrigatório"
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
                            nome to { setNomeError(it) },
                            contato to {setContatoError(it)},
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
                            viewModel.createBatalhao(
                                nome = nome,
                                contato = contato,
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
                        .padding(top = 16.dp),
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
                viewModel.reset()
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
                Text("Batalhão cadastrado com sucesso")
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
                    Text(("OK"))
                }
            }
        )
    }
}