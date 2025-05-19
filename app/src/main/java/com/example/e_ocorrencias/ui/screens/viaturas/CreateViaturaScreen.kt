package com.example.e_ocorrencias.ui.screens.viaturas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.data.models.request.ViaturaRequest
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.ui.components.RequiredField
import com.example.e_ocorrencias.ui.viewmodel.BatalhaoViewModel
import com.example.e_ocorrencias.ui.viewmodel.ViaturaViewModel
import com.example.e_ocorrencias.utils.getValue
import com.example.e_ocorrencias.utils.setValue


@Composable
fun CreateViaturaScreen(
    navController: NavController,
    viewModel: ViaturaViewModel = hiltViewModel(),
    viewModelBatalhao: BatalhaoViewModel = hiltViewModel()
) {

    val isSaving by viewModel.isSaving.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val batalhoes by viewModelBatalhao.batalhoes.collectAsState()

    val (showError, setShowError) = remember { mutableStateOf(false) }
    val (error, setError) = remember { mutableStateOf(false) }
    val (showSuccessDialog, setShowSuccessDialog) = remember { mutableStateOf(false) }
    var selectedBatalhao by remember { mutableStateOf<Batalhao?>(null) }

    var searchText by remember { mutableStateOf("") }

    val filteredBatalhoes = batalhoes.filter {
        it.nome.contains(searchText, ignoreCase = true)
    }


    var prefixo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }


    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            setShowSuccessDialog(true)
            navController.previousBackStackEntry?.savedStateHandle?.set("shouldRefresh", true)
        }
    }

    LaunchedEffect(Unit) {
        viewModelBatalhao.loadBatalhoes()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cadastrar Viatura",
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
                        contentDescription = "voltar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar",
                        fontSize = 14.sp,
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
                    value = prefixo,
                    onValueChange = { prefixo = it; setError(false) },
                    label = "Prefixo",
                    isError = error,
                    errorMessage = "O campo prefixo é obrigatório"
                )
                RequiredField(
                    value = placa,
                    onValueChange = { placa = it; setError(false) },
                    label = "Placa",
                    isError = error,
                    errorMessage = "O campo Placa é obrigatório"
                )
                RequiredField(
                    value = modelo,
                    onValueChange = { modelo = it; setError(false) },
                    label = "Modelo",
                    isError = error,
                    errorMessage = "O campo Modelo é obrigatório"
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    val textFieldSize = remember { mutableStateOf<Size?>(null) }

                    OutlinedTextField(
                        value = selectedBatalhao?.nome ?: searchText,
                        onValueChange = {
                            searchText = it
                            if (it != selectedBatalhao?.nome) {
                                selectedBatalhao = null
                            }
                        },
                        label = { Text("Buscar Batalhão") },
                        trailingIcon = {
                            if (selectedBatalhao != null) {
                                IconButton(onClick = {
                                    selectedBatalhao = null
                                    searchText = ""
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Limpar")
                                }
                            } else {
                                Icon(Icons.Default.Search, contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize.value = Size(
                                    coordinates.size.width.toFloat(),
                                    coordinates.size.height.toFloat()
                                )
                            }
                    )

                    if (searchText.isNotEmpty() && filteredBatalhoes.isNotEmpty() && selectedBatalhao == null) {
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = {
                                selectedBatalhao = null
                                searchText = ""
                            },
                            modifier = Modifier
                                .width(
                                    with(LocalDensity.current) {
                                        (textFieldSize.value?.width ?: 0f).toDp()
                                    }
                                )
                                .background(Color(0xFFF5F5F5))
                                .border(
                                    1.dp,
                                    Color.LightGray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            filteredBatalhoes.forEach { batalhao ->
                                val interactionSource = remember { MutableInteractionSource() }
                                val isHovered by interactionSource.collectIsHoveredAsState()

                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Place,
                                                contentDescription = null,
                                                tint = Color.Gray,
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .padding(end = 8.dp)
                                            )
                                            Text(text = batalhao.nome)
                                        }
                                    },
                                    onClick = {
                                        selectedBatalhao = batalhao
                                        searchText = batalhao.nome
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .background(
                                            color = if (isHovered) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .hoverable(interactionSource = interactionSource)
                                        .clickable {}
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (selectedBatalhao == null) {
                            setError(true)
                            setShowError(true)
                            return@Button
                        }

                        val camposInvalidos = listOf(
                            prefixo.isBlank(),
                            placa.isBlank(),
                            modelo.isBlank()
                        )

                        if (camposInvalidos.any { it }) {
                            setError(true)
                            setShowError(true)
                        } else {
                            setError(false)
                            viewModel.createViatura(
                                ViaturaRequest(
                                    prefixo = prefixo,
                                    placa = placa,
                                    modelo = modelo,
                                    batalhaoId = selectedBatalhao!!.id
                                ),
                                refresh = true
                            )
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "shouldRefresh",
                                true
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
                        disabledContentColor = Color.Gray
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
                navController.previousBackStackEntry?.savedStateHandle?.set("shouldRefresh", true)
                navController.popBackStack()
            },
            title = {
                Text(
                    text = "Sucesso",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF4CAF50)
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
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Viatura cadastrada com sucesso!",
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            setShowSuccessDialog(false)
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "shouldRefresh",
                                true
                            )
                            navController.popBackStack()
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
}