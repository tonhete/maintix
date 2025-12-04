package com.tonhete.maintixapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.tonhete.maintixapp.ui.components.MainScaffoldAdmin
import com.tonhete.maintixapp.ui.screens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "dashboard"
    val mantenimientoEnCurso = appState.mantenimientoEnCurso
    val numPendientes = appState.numPendientes

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = { tipoUsuarioId ->
                    when (tipoUsuarioId) {
                        1 -> navController.navigate("dashboard_admin") {
                            popUpTo("login") { inclusive = true }
                        }
                        2 -> navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                        else -> navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        // Dashboard Técnico
        composable("dashboard") {
            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                numPendientes = numPendientes,
                onNavigate = { navController.navigate(it) }
            ) {
                DashboardScreen(
                    onMantenimientoClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }
        }

        // Perfil Técnico
        composable("perfil") {
            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                numPendientes = numPendientes,
                onNavigate = { navController.navigate(it) }
            ) {
                UsuarioScreen(navController = navController)
            }
        }

// Perfil Admin
        composable("perfil_admin") {
            MainScaffoldAdmin(
                currentRoute = currentRoute,
                onNavigate = { navController.navigate(it) }
            ) {
                UsuarioScreen(navController = navController)
            }
        }

        // Detalle Mantenimiento (Técnico)
        composable(
            route = "detalle/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getInt("mantenimientoId") ?: 0

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                numPendientes = numPendientes,
                onNavigate = { navController.navigate(it) }
            ) {
                DetalleMantenimientoScreen(
                    mantenimientoId = mantenimientoId.toString(),
                    onIniciarClick = {
                        appState.iniciarMantenimiento(mantenimientoId.toString())
                        navController.navigate("checklist/$mantenimientoId")
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        // Checklist Técnico (editable)
        composable(
            route = "checklist/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getInt("mantenimientoId") ?: 0

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                numPendientes = numPendientes,
                onNavigate = { navController.navigate(it) }
            ) {
                ChecklistScreen(
                    mantenimientoId = mantenimientoId,
                    navController = navController,
                    soloLectura = false
                )
            }
        }

        // Dashboard Admin
        composable("dashboard_admin") {
            MainScaffoldAdmin(
                currentRoute = currentRoute,
                onNavigate = { navController.navigate(it) }
            ) {
                DashboardAdminScreen(navController = navController)
            }
        }

        // Checklist Admin (solo lectura)
        composable(
            route = "admin/checklist/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getInt("mantenimientoId") ?: 0

            MainScaffoldAdmin(
                currentRoute = currentRoute,
                onNavigate = { navController.navigate(it) }
            ) {
                ChecklistScreen(
                    mantenimientoId = mantenimientoId,
                    navController = navController,
                    soloLectura = true
                )
            }
        }

        composable("maquinas_admin") {
            MainScaffoldAdmin(
                currentRoute = currentRoute,
                onNavigate = { navController.navigate(it) }
            ) {
                MaquinasScreen(navController = navController)
            }
        }

        // Detalle Equipo Admin
        composable(
            route = "detalle_equipo/{equipoId}",
            arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: 0

            MainScaffoldAdmin(
                currentRoute = currentRoute,
                onNavigate = { navController.navigate(it) }
            ) {
                DetalleEquipoScreen(
                    equipoId = equipoId,
                    navController = navController
                )
            }
        }
    }
}

