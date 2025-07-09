package com.example.e_ocorrencias.ui.screens.welcome


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ocorrencias.R
import com.example.e_ocorrencias.ui.theme.GradientEndColor
import com.example.e_ocorrencias.ui.theme.GradientStartColor
import com.example.e_ocorrencias.ui.theme.welcomeButtonColors


@Composable
fun WelcomeScreen(navController: NavController) {
    val gradientColors = listOf(
        GradientStartColor,
        GradientEndColor
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "SISGOC",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.logo_governo_ceara),
                contentDescription = "Sigla da Polícia Militar do Ceará",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(vertical = 32.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Sistema de Gerenciamento de Ocorrências",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            Button(
                onClick = {
                    navController.navigate("login")
                },
                colors = welcomeButtonColors(),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Entrar", color = Color.White)
            }
        }
    }
}

