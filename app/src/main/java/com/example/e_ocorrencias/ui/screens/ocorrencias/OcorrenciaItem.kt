package com.example.e_ocorrencias.ui.screens.ocorrencias

import androidx.compose.foundation.clickable
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
import com.example.e_ocorrencias.data.models.response.Ocorrencia


@Composable
fun OcorrenciaItem(ocorrencia: Ocorrencia, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("ocorrencia_details/${ocorrencia.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ocorrência: ${ocorrencia.mOcorrencia}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Viatura: ${ocorrencia.viatura ?: "Não informado"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Policiais Envolvidos:", fontSize = 16.sp)
            ocorrencia.policiaisEnvolvidos.forEach { policial ->
                Text(
                    text = "- ${policial.nome} (${policial.matricula})",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(("Status: "))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = when (ocorrencia.status) {
                                "EM_ANDAMENTO" -> Color(0xFF1976D2)
                                "CONCLUIDA" -> Color(0xFF4CAF50)
                                "CANCELADA" -> Color(0xFFFF0000)
                                else -> Color.Gray
                            },
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(ocorrencia.status)
                    }
                },
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Comandante da Guarda: ${ocorrencia.comandanteGuarda ?: "Não informado"}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Registrado por: ${ocorrencia.registradoPor.nome} (${ocorrencia.registradoPor.matricula})",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Data Inicial: ${
                    ocorrencia.dataHoraInicial?.let { it } ?: "Data não informada"
                }", fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Data Final: ${
                    ocorrencia.dataHoraFinal?.let { it } ?: "Data não informada"
                }", fontSize = 16.sp
            )
        }
    }
}