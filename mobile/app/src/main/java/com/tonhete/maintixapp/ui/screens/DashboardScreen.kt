package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Pantalla principal: lista de mantenimientos pendientes
@Composable
fun DashboardScreen(onMantenimientoClick: (String) -> Unit) {

    // Lista de mantenimientos fake (después vendrá de la API)
    val mantenimientos = listOf(
        MantenimientoItem("1", "Torno CNC-450", "Preventivo"),
        MantenimientoItem("2", "Fresadora VMC-800", "Correctivo"),
        MantenimientoItem("3", "Compresor Atlas", "Preventivo")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Mantenimientos Pendientes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista scrollable de mantenimientos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(mantenimientos) { mantenimiento ->
                MantenimientoCard(
                    mantenimiento = mantenimiento,
                    onClick = { onMantenimientoClick(mantenimiento.id) }
                )
            }
        }
    }
}

// Card individual de mantenimiento
@Composable
fun MantenimientoCard(
    mantenimiento: MantenimientoItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)  // Al tocar, ejecuta onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Mantenimiento #${mantenimiento.id}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Máquina: ${mantenimiento.maquina}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tipo: ${mantenimiento.tipo}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Modelo de datos temporal (después usarás el de data/models)
data class MantenimientoItem(
    val id: String,
    val maquina: String,
    val tipo: String
)