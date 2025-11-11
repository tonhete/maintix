package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.Mantenimiento
import com.tonhete.maintixapp.data.models.Equipo
import kotlinx.coroutines.launch

// Pantalla de detalle del mantenimiento
@Composable
fun DetalleMantenimientoScreen(
    mantenimientoId: String,
    onIniciarClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var mantenimiento by remember { mutableStateOf<Mantenimiento?>(null) }
    var equipo by remember { mutableStateOf<Equipo?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // Cargar datos del mantenimiento y equipo
    LaunchedEffect(mantenimientoId) {
        scope.launch {
            try {
                val mantResponse = RetrofitClient.apiService.getMantenimiento(mantenimientoId.toInt())
                if (mantResponse.isSuccessful) {
                    mantenimiento = mantResponse.body()

                    // Cargar datos del equipo
                    mantenimiento?.equipoId?.let { equipoId ->
                        val equipoResponse = RetrofitClient.apiService.getEquipo(equipoId)
                        if (equipoResponse.isSuccessful) {
                            equipo = equipoResponse.body()
                        }
                    }
                } else {
                    errorMessage = "Error: ${mantResponse.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
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
        // Botón volver
        TextButton(onClick = onBackClick) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                // Título
                Text(
                    text = "Mantenimiento #$mantenimientoId",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Información del equipo
                equipo?.let { eq ->
                    InfoSection(
                        titulo = "Información de la Máquina",
                        contenido = buildString {
                            append("Máquina: ${eq.tipoMaquinaria?.descripcion ?: "N/A"}\n")
                            append("Número de serie: ${eq.numeroSerie ?: "N/A"}\n")
                            append("Horas actuales: ${eq.horasActuales ?: 0}\n")
                            append("Fecha fabricación: ${eq.fechaFabricacion ?: "N/A"}")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información del mantenimiento
                mantenimiento?.let { mant ->
                    InfoSection(
                        titulo = "Información del Mantenimiento",
                        contenido = buildString {
                            append("Estado: ${mant.estado}\n")
                            append("Fecha inicio: ${mant.fechaInicio ?: "N/A"}")
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón iniciar
                Button(
                    onClick = onIniciarClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("INICIAR MANTENIMIENTO")
                }
            }
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