package com.example.e_ocorrencias.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_ocorrencias.ui.viewmodel.AuthViewModel

@Composable
fun ProtectedRoute(
    requiredRole: String? = null,
    navController: NavController,
    content: @Composable () -> Unit
) {

    val authViewModel: AuthViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        if (!authViewModel.isUserLoggedIn()) {
            navController.navigate("login") {
                popUpTo(0)
            }
        } else if (requiredRole != null && !authViewModel.hasRole(requiredRole)) {
            navController.navigate("Unauthorized") {
                popUpTo(0)
            }
        }
    }

    if (authViewModel.isUserLoggedIn() &&
        (requiredRole == null || authViewModel.hasRole(requiredRole))
    ) {
        content()
    }
}
