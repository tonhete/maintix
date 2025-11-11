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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "dashboard"
    val mantenimientoEnCurso = appState.mantenimientoEnCurso

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("dashboard") }
            )
        }

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

        composable("perfil") {
            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                Text("Perfil - Próximamente")
            }
        }

        composable(
            route = "detalle/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getInt("mantenimientoId") ?: 0

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
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

        composable(
            route = "checklist/{mantenimientoId}",
            arguments = listOf(navArgument("mantenimientoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mantenimientoId = backStackEntry.arguments?.getInt("mantenimientoId") ?: 0

            MainScaffold(
                currentRoute = currentRoute,
                mantenimientoEnCurso = mantenimientoEnCurso,
                onNavigate = { navController.navigate(it) }
            ) {
                ChecklistScreen(
                    mantenimientoId = mantenimientoId,
                    navController = navController
                )
            }
        }
    }
}