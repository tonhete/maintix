package com.tonhete.maintixapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tonhete.maintixapp.data.appState
import com.tonhete.maintixapp.ui.components.MainScaffold
import com.tonhete.maintixapp.ui.screens.*

// Navegación completa de la app
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "dashboard"

    // Observa el estado global del mantenimiento
    val mantenimientoEnCurso = appState.mantenimientoEnCurso

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Login (SIN bottom bar)
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("dashboard") }
            )
        }

        // Dashboard (CON bottom bar)
        composable("dashboard") {
            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                DashboardScreen(
                    onMantenimientoClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }
        }

        // Perfil (CON bottom bar)
        composable("perfil") {
            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                Text("Perfil - Próximamente")
            }
        }

        // Detalle (CON bottom bar ahora)
        composable(
            route = "detalle/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getString("mantenimientoId") ?: ""

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                DetalleMantenimientoScreen(
                    mantenimientoId = mantenimientoId,
                    onIniciarClick = {
                        // Marca el mantenimiento como en curso
                        appState.iniciarMantenimiento(mantenimientoId)
                        navController.navigate("checklist/$mantenimientoId")
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        // Checklist (CON bottom bar ahora)
        composable(
            route = "checklist/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getString("mantenimientoId") ?: ""

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                ChecklistScreen(
                    mantenimientoId = mantenimientoId,
                    onFinalizarClick = {
                        // Marca el mantenimiento como finalizado
                        appState.finalizarMantenimiento()
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = false }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}