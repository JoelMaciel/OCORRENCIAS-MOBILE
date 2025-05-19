package com.example.e_ocorrencias.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_ocorrencias.ui.screens.acusados.AcusadosScreen
import com.example.e_ocorrencias.ui.screens.armas.ArmasScreen
import com.example.e_ocorrencias.ui.screens.batalhoes.BatalhaoCreateScreen
import com.example.e_ocorrencias.ui.screens.batalhoes.BatalhaoDetailsScreen
import com.example.e_ocorrencias.ui.screens.batalhoes.BatalhaoEditScreen
import com.example.e_ocorrencias.ui.screens.batalhoes.BatalhaoListScreen
import com.example.e_ocorrencias.ui.screens.batalhoes.BatalhaoSearchScreen
import com.example.e_ocorrencias.ui.screens.corpos_guarda.CorpoGuardaScreen
import com.example.e_ocorrencias.ui.screens.login.LoginScreen
import com.example.e_ocorrencias.ui.screens.ocorrencias.OcorrenciaCreateScreen
import com.example.e_ocorrencias.ui.screens.ocorrencias.OcorrenciaDetailsScreen
import com.example.e_ocorrencias.ui.screens.ocorrencias.OcorrenciaEditScreen
import com.example.e_ocorrencias.ui.screens.ocorrencias.OcorrenciaListScreen
import com.example.e_ocorrencias.ui.screens.ocorrencias.OcorrenciaSearchScreen
import com.example.e_ocorrencias.ui.screens.policiais.PoliciaisScreen
import com.example.e_ocorrencias.ui.screens.topbar.TopBar
import com.example.e_ocorrencias.ui.screens.viaturas.CreateViaturaScreen
import com.example.e_ocorrencias.ui.screens.viaturas.SearchViaturasScreen
import com.example.e_ocorrencias.ui.screens.viaturas.ViaturasListScreen
import com.example.e_ocorrencias.ui.screens.vitimas.VitimasScreen
import com.example.e_ocorrencias.ui.screens.welcome.WelcomeScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        MenuItemData("Ocorrências", Icons.Default.List, "ocorrencias"),
        MenuItemData("Policiais", Icons.Default.Person, "policiais"),
        MenuItemData("Batalhões", Icons.Default.Security, "batalhoes"),
        MenuItemData("Viaturas", Icons.Default.DirectionsCar, "viaturas"),
        MenuItemData("Corpo da Guarda", Icons.Default.Security, "corpoGuarda"),
        MenuItemData("Armas", Icons.Default.Warning, "armas"),
        MenuItemData("Acusados", Icons.Default.Gavel, "acusados"),
        MenuItemData("Vítimas", Icons.Default.Face, "vitimas")
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    LaunchedEffect(currentRoute) {
        if (currentRoute != "welcome" && currentRoute != "login") {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentRoute != "welcome" && currentRoute != "login",
        drawerContent = {
            if (currentRoute != "welcome" && currentRoute != "login") {
                DrawerContent(
                    menuItems = menuItems,
                    currentRoute = currentRoute,
                    onItemSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentRoute != "welcome" && currentRoute != "login") {
                    TopBar(drawerState, scope)
                }
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = "welcome",
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("login") { LoginScreen(navController) }

                    composable("ocorrencias") { OcorrenciaListScreen(navController) }
                    composable("ocorrencia_create") { OcorrenciaCreateScreen(navController) }
                    composable("ocorrencia_search") { OcorrenciaSearchScreen(navController) }
                    composable("ocorrencia_details/{ocorrenciaId}") { backStackEntry ->
                        val ocorrenciaId = backStackEntry.arguments?.getString("ocorrenciaId")
                        if (ocorrenciaId != null) {
                            OcorrenciaDetailsScreen(navController, ocorrenciaId)
                        }
                    }
                    composable("ocorrencia_edit/{ocorrenciaId}") { backStackEntry ->
                        val ocorrenciaId = backStackEntry.arguments?.getString("ocorrenciaId")
                        if (ocorrenciaId != null) {
                            OcorrenciaEditScreen(navController, ocorrenciaId)
                        }
                    }

                    composable("batalhoes") { BatalhaoListScreen(navController) }
                    composable("batalhao_search") { BatalhaoSearchScreen(navController) }
                    composable("batalhao_create") { BatalhaoCreateScreen(navController) }
                    composable("batalhao_details/{batalhaoId}") { backStackEntry ->
                        val batalhaoId = backStackEntry.arguments?.getString("batalhaoId")
                        if (batalhaoId != null) {
                            BatalhaoDetailsScreen(navController, batalhaoId)
                        }
                    }
                    composable("batalhao_edit/{batalhaoId}") { backStackEntry ->
                        val batalhaoId = backStackEntry.arguments?.getString("batalhaoId")
                        if (batalhaoId != null) {
                            BatalhaoEditScreen(navController, batalhaoId)
                        }
                    }

                    composable("viaturas") { ViaturasListScreen(navController) }
                    composable("viatura_create") { CreateViaturaScreen(navController) }
                    composable("viatura_search") { SearchViaturasScreen(navController) }
                    composable("policiais") { PoliciaisScreen(navController) }
                    composable("corpoGuarda") { CorpoGuardaScreen(navController) }
                    composable("armas") { ArmasScreen(navController) }
                    composable("acusados") { AcusadosScreen(navController) }
                    composable("vitimas") { VitimasScreen(navController) }
                }
            }
        )
    }
}


