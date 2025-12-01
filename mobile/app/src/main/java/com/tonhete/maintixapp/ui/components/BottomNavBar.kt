package com.tonhete.maintixapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier


// mantenimientoEnCurso: ID del mantenimiento activo o null
@Composable
fun MainScaffold(
    currentRoute: String,
    mantenimientoEnCurso: String?,
    numPendientes: Int = 0,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                // Tab Dashboard
                NavigationBarItem(
                    selected = currentRoute.startsWith("dashboard"),
                    onClick = { onNavigate("dashboard") },
                    icon = {
                        if (numPendientes > 0) {
                            BadgedBox(
                                badge = { Badge { Text(numPendientes.toString()) } }
                            ) {
                                Icon(Icons.Default.Home, "Inicio")
                            }
                        } else {
                            Icon(Icons.Default.Home, "Inicio")
                        }
                    },
                    label = { Text("Inicio") }
                )

                // Tab Mantenimiento en curso (solo si hay uno activo)
                if (mantenimientoEnCurso != null) {
                    NavigationBarItem(
                        selected = currentRoute.startsWith("checklist") || currentRoute.startsWith("detalle"),
                        onClick = { onNavigate("checklist/$mantenimientoEnCurso") },
                        icon = { Icon(Icons.Default.Build, "En curso") },
                        label = { Text("En curso") }
                    )
                }

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