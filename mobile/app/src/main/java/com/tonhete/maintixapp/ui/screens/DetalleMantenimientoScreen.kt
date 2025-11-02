package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Pantalla de detalle del mantenimiento
// Muestra info de la máquina antes de iniciar
@Composable
fun DetalleMantenimientoScreen(
    mantenimientoId: String,
    onIniciarClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón volver
        TextButton(onClick = onBackClick) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Mantenimiento #$mantenimientoId",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Información (fake por ahora)
        InfoSection(
            titulo = "Información General",
            contenido = """
                Máquina: Torno CNC-450
                Tipo: Preventivo Trimestral
                Fecha programada: 15/11/2025
                Responsable: Juan Pérez
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoSection(
            titulo = "Localización",
            contenido = """
                Planta: Planta Baja
                Zona: Área de Producción 2
                Línea: Línea 4
            """.trimIndent()
        )

        Spacer(modifier = Modifier.weight(1f))  // Empuja el botón abajo

        // Botón grande para iniciar
        Button(
            onClick = onIniciarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("INICIAR MANTENIMIENTO")
        }
    }
}

// Componente reutilizable para secciones de info
@Composable
fun InfoSection(titulo: String, contenido: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}