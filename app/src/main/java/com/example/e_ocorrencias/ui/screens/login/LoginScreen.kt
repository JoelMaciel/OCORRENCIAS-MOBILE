package com.example.e_ocorrencias.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.R
import com.example.e_ocorrencias.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val (error, setError) = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val authResult by authViewModel.authResult.collectAsState()
    val scope = rememberCoroutineScope()

    val placeholderColor = Color(0xFFA0A0A0)
    val backgroundColor = Color(0xFFF5F5F5)
    val cardColor = Color.White

    val focusedBorderColor = Color(0xFF455A64)
    val unfocusedBorderColor = Color(0xFF263238)
    val roundedCornerShape = RoundedCornerShape(16.dp)

    LaunchedEffect(authResult) {
        when (authResult) {
            is AuthViewModel.AuthResult.Success -> {
                navController.navigate("Ocorrencias") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is AuthViewModel.AuthResult.Error -> {
                val error = authResult as AuthViewModel.AuthResult.Error
                errorMessage.value = when (error.code) {
                    401 -> "Email ou senha incorretos"
                    else -> error.message
                }
                setError(true)
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "SISGOC",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = roundedCornerShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_pmce),
                        contentDescription = "Logo da Polícia Militar do Ceará",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(bottom = 24.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = "Login",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        label = { Text("Email") },
                        isError = error,
                        supportingText = {
                            if (emailError) {
                                Text(
                                    text = "O campo Email é obrigatório.",
                                    color = Color.Red,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedIndicatorColor = if (error) Color.Red else focusedBorderColor,
                            unfocusedIndicatorColor = if (error) Color.Red else unfocusedBorderColor,
                            errorIndicatorColor = Color.Red,
                            focusedLabelColor = focusedBorderColor,
                            unfocusedLabelColor = placeholderColor,
                            errorLabelColor = Color.Red
                        ),
                        shape = roundedCornerShape,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = false
                        },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = error,
                        supportingText = {
                            if (passwordError) {
                                Text(
                                    text = "O campo Senha é obrigatório.",
                                    color = Color.Red,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedIndicatorColor = if (error) Color.Red else focusedBorderColor,
                            unfocusedIndicatorColor = if (error) Color.Red else unfocusedBorderColor,
                            errorIndicatorColor = Color.Red,
                            focusedLabelColor = focusedBorderColor,
                            unfocusedLabelColor = placeholderColor,
                            errorLabelColor = Color.Red
                        ),
                        shape = roundedCornerShape,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val isEmailEmpty = email.isBlank()
                            val isPasswordEmpty = password.isBlank()

                            emailError = false
                            passwordError = false

                            if (isEmailEmpty || isPasswordEmpty) {
                                if (isEmailEmpty) emailError = true
                                if (isPasswordEmpty) passwordError = true
                                errorMessage.value = "Por favor, preencha todos os campos."
                            } else {
                                scope.launch(Dispatchers.IO) {
                                    authViewModel.login(email, password)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Entrar", fontSize = 18.sp)
                    }

                    if (error) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Credenciais inválidas. Tente novamente.",
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}



