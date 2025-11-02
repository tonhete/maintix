package com.tonhete.maintixapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// Bottom Navigation con lógica especial
// mantenimientoEnCurso: ID del mantenimiento activo o null
@Composable
fun MainScaffold(
    currentRoute: String,
    mantenimientoEnCurso: String?,  // ← Nuevo parámetro
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                // Tab Dashboard
                NavigationBarItem(
                    selected = currentRoute.startsWith("dashboard"),
                    onClick = { onNavigate("dashboard") },
                    icon = {
                        BadgedBox(
                            badge = { Badge { Text("3") } }
                        ) {
                            Icon(Icons.Default.Home, "Inicio")
                        }
                    },
                    label = { Text("Inicio") }
                )

                // Tab Maquinaria/Mantenimiento en curso
                NavigationBarItem(
                    selected = currentRoute.startsWith("checklist") || currentRoute.startsWith("detalle"),
                    onClick = {
                        // Si hay mantenimiento en curso, va al checklist
                        // Si no, va al dashboard
                        if (mantenimientoEnCurso != null) {
                            onNavigate("checklist/$mantenimientoEnCurso")
                        } else {
                            onNavigate("dashboard")
                        }
                    },
                    icon = {
                        Icon(
                            if (mantenimientoEnCurso != null) Icons.Default.Build else Icons.Default.List,
                            "Máquinas"
                        )
                    },
                    label = {
                        Text(if (mantenimientoEnCurso != null) "En curso" else "Máquinas")
                    }
                )

                // Tab Perfil
                NavigationBarItem(
                    selected = currentRoute.startsWith("perfil"),
                    onClick = { onNavigate("perfil") },
                    icon = { Icon(Icons.Default.Person, "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}