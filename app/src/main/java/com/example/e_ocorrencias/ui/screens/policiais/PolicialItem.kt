package com.example.e_ocorrencias.ui.screens.policiais

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.e_ocorrencias.data.models.response.Policial

@Composable
fun PolicialItem(policial: Policial) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nome: ${policial.nome}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Matricula: ${policial.matricula}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Posto-Graduação: ${policial.postoGraduacao}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "CPF: ${policial.cpf}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Contato: ${policial.contato}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "E-mail: ${policial.email}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Batalhao: ${policial.batalhao}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Data Cadastro: ${policial.createdAt}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Data Atualização: ${policial.updatedAt}")
        }
    }
}