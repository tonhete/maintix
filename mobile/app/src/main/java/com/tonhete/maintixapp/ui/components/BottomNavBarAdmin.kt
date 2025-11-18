package com.tonhete.maintixapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScaffoldAdmin(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute.startsWith("dashboard_admin"),
                    onClick = { onNavigate("dashboard_admin") },
                    icon = { Icon(Icons.Default.Home, "Inicio") },
                    label = { Text("Inicio") }
                )

                NavigationBarItem(
                    selected = currentRoute.startsWith("maquinas_admin"),
                    onClick = { onNavigate("maquinas_admin") },
                    icon = { Icon(Icons.Default.Settings, "Máquinas") },
                    label = { Text("Máquinas") }
                )

                NavigationBarItem(
                    selected = currentRoute.startsWith("perfil_admin"),
                    onClick = { onNavigate("perfil_admin") },
                    icon = { Icon(Icons.Default.Person, "Usuario") },
                    label = { Text("Usuario") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}