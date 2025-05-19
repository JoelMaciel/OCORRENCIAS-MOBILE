package com.example.e_ocorrencias.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import  androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun RequiredField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = if (isError) Color.Red else Color.Gray) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(errorMessage, color = Color.Red)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(
        modifier = Modifier.height(
            8.dp
        )
    )
}