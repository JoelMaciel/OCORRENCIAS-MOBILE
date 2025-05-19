package com.example.e_ocorrencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.e_ocorrencias.ui.navigation.AppNavigation
import com.example.e_ocorrencias.ui.theme.E_ocorrenciasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            E_ocorrenciasTheme {
               AppNavigation()
            }
        }
    }
}

