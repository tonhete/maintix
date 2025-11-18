package com.tonhete.maintixapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.Mantenimiento
import kotlinx.coroutines.launch
import com.tonhete.maintixapp.data.appState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(onMantenimientoClick: (String) -> Unit) {

    var mantenimientos by remember { mutableStateOf<List<Mantenimiento>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val tecnicoId = appState.tecnicoId ?: return@launch
                val response = RetrofitClient.apiService.getMantenimientosPorTecnico(tecnicoId)

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

    // Ordenar: primero no finalizados, finalizados al final
    val listaOrdenada = mantenimientos.sortedBy { it.estado == "finalizado" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Mantenimientos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Error desconocido",
                    color = MaterialTheme.colorScheme.error
                )
            }

            listaOrdenada.isEmpty() -> {
                Text("No hay mantenimientos asignados")
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listaOrdenada) { mantenimiento ->
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MantenimientoCard(
    mantenimiento: Mantenimiento,
    onClick: () -> Unit
) {
    // Color según estado
    val badgeColor = when (mantenimiento.estado) {
        "finalizado" -> Color(0xFF4CAF50)
        "en_progreso" -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            // Barra superior con estado
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = badgeColor
            ) {
                Text(
                    text = mantenimiento.estado.uppercase(),
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Contenido del card
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Nombre de la máquina (una línea)
                Text(
                    text = mantenimiento.maquinaNombre ?: "Máquina #${mantenimiento.equipoId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                // Número de serie
                Text(
                    text = "S/N: ${mantenimiento.numeroSerie ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tipo
                Text(
                    text = "Tipo: ${mantenimiento.tipoMantenimiento?.nombre ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Fecha
                mantenimiento.fechaInicio?.let {
                    Text(
                        text = "Fecha: ${formatearFecha(it)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Progreso (solo si no finalizado)
                if (mantenimiento.estado != "finalizado") {
                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { mantenimiento.progresoChecklist },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "${(mantenimiento.progresoChecklist * 100).toInt()}% completado",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}


