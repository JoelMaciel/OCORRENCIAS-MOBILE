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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.e_ocorrencias.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val (showError, setShowError) = remember { mutableStateOf(false) }
    val (emailError, setEmailError) = remember { mutableStateOf(false) }
    val (passwordError, setPasswordError) = remember { mutableStateOf(false) }

    val placeholderColor = Color(0xFFA0A0A0)
    val backgroundColor = Color(0xFFF5F5F5)
    val cardColor = Color.White

    val focusedBorderColor = Color(0xFF455A64)
    val unfocusedBorderColor = Color(0xFF263238)
    val roundedCornerShape = RoundedCornerShape(16.dp)

    val mockEmail = "joel@example.com"
    val mockPassword = "senha123"

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
                        onValueChange = { email = it; setEmailError(false) },
                        label = { Text("Email") },
                        isError = emailError,
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
                            focusedIndicatorColor = if (emailError) Color.Red else focusedBorderColor,
                            unfocusedIndicatorColor = if (emailError) Color.Red else unfocusedBorderColor,
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
                        onValueChange = { password = it; setPasswordError(false) },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = passwordError,
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
                            focusedIndicatorColor = if (passwordError) Color.Red else focusedBorderColor,
                            unfocusedIndicatorColor = if (passwordError) Color.Red else unfocusedBorderColor,
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

                            if (isEmailEmpty || isPasswordEmpty) {
                                setEmailError(isEmailEmpty)
                                setPasswordError(isPasswordEmpty)
                            } else if (email == mockEmail && password == mockPassword) {
                                navController.navigate("ocorrencias") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                setShowError(true)
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

                    if (showError) {
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



