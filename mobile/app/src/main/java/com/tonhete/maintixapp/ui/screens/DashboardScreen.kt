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
    val progreso = mantenimiento.progresoChecklist.toFloat()
    val porcentaje = (progreso * 100).toInt()

    // Colores reales exactos
    val rojoClaro = Color(0xFFFFCDD2)
    val naranjaClaro = Color(0xFFFFE0B2)
    val verdeClaro = Color(0xFFC8E6C9)

    val fondoCard = when {
        mantenimiento.estado == "finalizado" -> verdeClaro
        progreso == 0f -> rojoClaro
        progreso > 0f && progreso < 1f -> naranjaClaro
        else -> rojoClaro
    }

    val colorBarra = Color(0xFF1976D2) // color de la barra rellena

    val fecha = mantenimiento.fechaInicio?.let {
        try {
            LocalDateTime.parse(it).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        } catch (_: Exception) { it }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (mantenimiento.estado == "finalizado") 20.dp else 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = fondoCard)
    ) {

        Column(Modifier.padding(16.dp)) {

            Text("Mantenimiento #${mantenimiento.id}", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(6.dp))

            Text("Equipo ID: ${mantenimiento.equipoId}")
            Text("Máquina: ")
            Text("Estado: ${mantenimiento.estado}")

            fecha?.let { Text("Fecha: $it") }

            Spacer(Modifier.height(10.dp))

            Text("$porcentaje%")

            Spacer(Modifier.height(6.dp))

            // Barra sin fondo, solo progreso
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progreso)
                        .clip(RoundedCornerShape(50))
                        .background(colorBarra)
                )
            }
        }
    }
}
