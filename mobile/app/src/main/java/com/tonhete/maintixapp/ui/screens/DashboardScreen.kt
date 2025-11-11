package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.Mantenimiento
import kotlinx.coroutines.launch

// Pantalla principal: lista de mantenimientos desde la API
@Composable
fun DashboardScreen(onMantenimientoClick: (String) -> Unit) {

    // Estado para guardar los mantenimientos
    var mantenimientos by remember { mutableStateOf<List<Mantenimiento>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Scope para llamadas asíncronas
    val scope = rememberCoroutineScope()

    // Cargar mantenimientos al iniciar
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getMantenimientos()
                if (response.isSuccessful) {
                    mantenimientos = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

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

        // Estados de carga/error
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Error desconocido",
                    color = MaterialTheme.colorScheme.error
                )
            }
            mantenimientos.isEmpty() -> {
                Text("No hay mantenimientos pendientes")
            }
            else -> {
                // Lista de mantenimientos
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mantenimientos) { mantenimiento ->
                        MantenimientoCard(
                            mantenimiento = mantenimiento,
                            onClick = { onMantenimientoClick(mantenimiento.id.toString()) }
                        )
                    }
                }
            }
        }
    }
}

// Card individual de mantenimiento
@Composable
fun MantenimientoCard(
    mantenimiento: Mantenimiento,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                text = "Equipo ID: ${mantenimiento.equipoId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${mantenimiento.estado}",
                style = MaterialTheme.typography.bodyMedium
            )
            mantenimiento.fechaInicio?.let {  // ← Cambiado aquí
                Text(
                    text = "Fecha: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}