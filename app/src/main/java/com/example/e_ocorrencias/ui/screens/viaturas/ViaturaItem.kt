package com.example.e_ocorrencias.ui.screens.viaturas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ocorrencias.data.models.response.Viatura

@Composable
fun ViaturaItem(viatura: Viatura, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Prefixo: ${viatura.prefixo}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Batalhão: ${viatura.batalhao}")
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(("Status: "))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = when (viatura.status) {
                                "ATIVA" -> Color(0xFF34C759)
                                "INATIVA" -> Color(0xFFFFA07A)
                                "EM_SERVIÇO" -> Color(0xFF2196F3)
                                "MANUTENÇÃO" -> Color(0xFFE74C3C)
                                else -> Color.Gray
                            },
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(viatura.status)
                    }
                },
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Modelo: ${viatura.modelo}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Placa: ${viatura.placa}")
        }
    }
}