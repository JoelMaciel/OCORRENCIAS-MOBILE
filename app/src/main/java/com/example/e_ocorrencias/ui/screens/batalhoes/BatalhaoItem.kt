package com.example.e_ocorrencias.ui.screens.batalhoes

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_ocorrencias.data.models.response.Batalhao
import com.example.e_ocorrencias.utils.DateUtils

@Composable
fun BatalhaoItem(
    batalhao: Batalhao,
    navController: NavController,
    isForSelection: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (isForSelection) {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "selectedBatalhao",
                        batalhao
                    )
                    navController.popBackStack()
                } else {
                    navController.navigate("batalhao_details/${batalhao.id}")
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Nome: ${batalhao.nome}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Contato: ${batalhao.contato}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Endereço:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Rua: ${batalhao.endereco.rua}", fontSize = 16.sp)
            Text(text = "Número: ${batalhao.endereco.numero}", fontSize = 16.sp)
            if (batalhao.endereco.complemento.isNullOrEmpty()) {
                Text(text = "Complemento: ${batalhao.endereco.complemento}", fontSize = 14.sp)
            }
            Text(text = "Bairro: ${batalhao.endereco.bairro}", fontSize = 16.sp)
            Text(text = "Cidade: ${batalhao.endereco.cidade}", fontSize = 16.sp)
            Text(text = "UF: ${batalhao.endereco.uf}", fontSize = 16.sp)
            Text(text = "CEP: ${batalhao.endereco.cep}", fontSize = 16.sp)

            Text(
                text = "Data Criação: ${DateUtils.formatDate(batalhao.dataCriacao)}",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Data Atualização: ${DateUtils.formatDate(batalhao.dataAtualizacao)}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
