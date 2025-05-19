package com.example.e_ocorrencias.ui.screens.policiais

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.viewmodel.PoliciaViewModel
import com.example.e_ocorrencias.utils.getValue


@Composable
fun PoliciaisScreen(navController: NavController, viewModel: PoliciaViewModel = hiltViewModel()) {
    val policiais by viewModel.policiais
    val isLoading by viewModel.isLoading
    val currentPage by viewModel.currentPage
    val totalPages by viewModel.totalPages
    val error by viewModel.error

    LaunchedEffect(Unit) {
        viewModel.loadPoliciais()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Policiais",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (error != null) {
            Text(
                text = error!!,
                fontSize = 18.sp,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            LazyColumn {
                itemsIndexed(policiais) { index, policial ->
                    PolicialItem(policial)
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
                            onClick = { viewModel.loadPoliciais() },
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
}


