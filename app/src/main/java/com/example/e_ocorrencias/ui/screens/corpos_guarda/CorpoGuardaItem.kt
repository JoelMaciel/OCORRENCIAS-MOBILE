package com.example.e_ocorrencias.ui.screens.corpos_guarda

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
import androidx.compose.ui.unit.dp
import com.example.e_ocorrencias.data.models.response.CorpoGuarda
import  com.example.e_ocorrencias.utils.DateUtils


@Composable
fun CorpoGuardaItem(corpoGuarda: CorpoGuarda) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Batalhão: ${corpoGuarda.batalhao}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Comandante: ${corpoGuarda.comandante ?: "Não informado"}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Posto/Graduação: ${corpoGuarda.postoGraduacao}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Policiais Auxiliares:")
            corpoGuarda.policiais.forEach { policial ->
                Text(text = "- ${policial.nome} (${policial.graduacao})")
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Data Criação: ${
                    corpoGuarda.dataCriacao?.let { DateUtils.formatDate(it) } ?: "Data não informada"
                }"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Data Atualização: ${
                    corpoGuarda.dataAtualizacao?.let { DateUtils.formatDate(it) } ?: "Data não informada"
                }"
            )
        }
    }
}