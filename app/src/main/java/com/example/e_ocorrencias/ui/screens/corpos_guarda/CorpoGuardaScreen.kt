package com.example.e_ocorrencias.ui.screens.corpos_guarda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.viewmodel.CorpoGuardaViewModel
import com.example.e_ocorrencias.utils.getValue

@Composable
fun CorpoGuardaScreen(navController: NavController, viewModel: CorpoGuardaViewModel = hiltViewModel()) {
    val corposGuarda by viewModel.corposGuarda
    val isLoading by viewModel.isLoading
    val currentPage by viewModel.currentPage
    val totalPages by viewModel.totalPages

    LaunchedEffect(Unit) {
        viewModel.loadCorpoGuarda()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Corpo da Guarda",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            itemsIndexed(corposGuarda) { index, guarda ->
                CorpoGuardaItem(guarda)
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            if (currentPage < totalPages) {
                item {
                    Button(
                        onClick = { viewModel.loadCorpoGuarda() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Carregar mais")
                    }
                }
            }
        }
    }
}