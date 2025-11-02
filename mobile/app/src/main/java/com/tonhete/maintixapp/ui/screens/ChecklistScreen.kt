package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Pantalla del checklist: lista de items a completar
@Composable
fun ChecklistScreen(
    mantenimientoId: String,
    onFinalizarClick: () -> Unit,
    onBackClick: () -> Unit
) {
    // Lista de items del checklist (fake)
    // Guardamos el estado de cada item (completado o no)
    var items by remember {
        mutableStateOf(
            listOf(
                CheckItem("1", "Revisión nivel aceite", false),
                CheckItem("2", "Limpieza filtros", false),
                CheckItem("3", "Tensión correas", false),
                CheckItem("4", "Verificar sensores", false)
            )
        )
    }

    // Calcula cuántos items están completados
    val completados = items.count { it.completado }
    val total = items.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón volver
        TextButton(onClick = onBackClick) {
            Text("← Volver")
        }

        // Título
        Text(
            text = "Checklist Mantenimiento #$mantenimientoId",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress indicator
        Text(
            text = "$completados/$total items completados",
            style = MaterialTheme.typography.bodyLarge
        )
        LinearProgressIndicator(
            progress = { completados.toFloat() / total },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de items
        LazyColumn(
            modifier = Modifier.weight(1f),  // Ocupa el espacio disponible
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                CheckItemCard(
                    item = item,
                    onCheckChange = { checked ->
                        // Actualiza el estado del item
                        items = items.map {
                            if (it.id == item.id) it.copy(completado = checked)
                            else it
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón finalizar (solo habilitado si todos completados)
        Button(
            onClick = onFinalizarClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = completados == total  // Solo activo si todos completados
        ) {
            Text("FINALIZAR MANTENIMIENTO")
        }
    }
}

// Card de un item del checklist
@Composable
fun CheckItemCard(
    item: CheckItem,
    onCheckChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Checkbox
            Checkbox(
                checked = item.completado,
                onCheckedChange = onCheckChange
            )
        }
    }
}

// Modelo de datos temporal
data class CheckItem(
    val id: String,
    val nombre: String,
    val completado: Boolean
)