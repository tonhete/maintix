package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.AsignarOperarioDto
import com.tonhete.maintixapp.data.models.Mantenimiento
import com.tonhete.maintixapp.data.models.Usuario
import com.tonhete.maintixapp.ui.components.MaintixButton
import com.tonhete.maintixapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAdminScreen(navController: NavController) {
    var mantenimientos by remember { mutableStateOf<List<Mantenimiento>>(emptyList()) }
    var tecnicos by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var mantenimientoSeleccionado by remember { mutableStateOf<Mantenimiento?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        scope.launch {
            try {
                isLoading = true
                val mantenimientosResponse = RetrofitClient.apiService.getMantenimientos()
                if (mantenimientosResponse.isSuccessful) {
                    mantenimientos = mantenimientosResponse.body() ?: emptyList()
                }

                val usuariosResponse = RetrofitClient.apiService.getUsuarios()
                if (usuariosResponse.isSuccessful) {
                    tecnicos = (usuariosResponse.body() ?: emptyList())
                        .filter { it.tipoUsuarioId == 2 }
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
                isRefreshing = false
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarDatos()
    }

    val sinAsignar = mantenimientos.filter { it.operarioAsignadoId == null }
    val pendientes = mantenimientos.filter {
        it.operarioAsignadoId != null && (it.estado == "pendiente" || it.estado == "en_progreso")
    }
    val finalizados = mantenimientos.filter { it.estado == "finalizado" }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            cargarDatos()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Panel Administrador",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (sinAsignar.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Sin asignar (${sinAsignar.size})",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            items(sinAsignar) { mantenimiento ->
                                MantenimientoAdminCard(
                                    mantenimiento = mantenimiento,
                                    color = Color(0xFF2196F3),
                                    mostrarProgreso = false,
                                    estadoTexto = "PENDIENTE DE ASIGNACIÓN",
                                    onClick = { mantenimientoSeleccionado = mantenimiento }
                                )
                            }
                            item { Spacer(modifier = Modifier.height(8.dp)) }
                        }

                        if (pendientes.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Pendientes (${pendientes.size})",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            items(pendientes) { mantenimiento ->
                                MantenimientoAdminCard(
                                    mantenimiento = mantenimiento,
                                    color = Color(0xFFFF9800),
                                    mostrarProgreso = true,
                                    estadoTexto = mantenimiento.estado.uppercase(),
                                    onClick = { navController.navigate("admin/checklist/${mantenimiento.id}") }
                                )
                            }
                            item { Spacer(modifier = Modifier.height(8.dp)) }
                        }

                        if (finalizados.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Finalizados (${finalizados.size})",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            items(finalizados) { mantenimiento ->
                                MantenimientoAdminCard(
                                    mantenimiento = mantenimiento,
                                    color = Color(0xFF4CAF50),
                                    mostrarProgreso = false,
                                    estadoTexto = "FINALIZADO",
                                    onClick = { navController.navigate("admin/checklist/${mantenimiento.id}") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    mantenimientoSeleccionado?.let { mantenimiento ->
        AsignarTecnicoModal(
            mantenimiento = mantenimiento,
            tecnicos = tecnicos,
            onDismiss = { mantenimientoSeleccionado = null },
            onAsignar = { tecnicoId ->
                scope.launch {
                    try {
                        val response = RetrofitClient.apiService.asignarOperario(
                            mantenimientoId = mantenimiento.id,
                            body = AsignarOperarioDto(tecnicoId)
                        )

                        if (response.isSuccessful) {
                            val nuevosMantenimientos = RetrofitClient.apiService.getMantenimientos()
                            if (nuevosMantenimientos.isSuccessful) {
                                mantenimientos = nuevosMantenimientos.body() ?: emptyList()
                            }
                            mantenimientoSeleccionado = null
                        } else {
                            errorMessage = "Error al asignar: ${response.code()}"
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error: ${e.message}"
                    }
                }
            }
        )
    }
}


@Composable
fun MantenimientoAdminCard(
    mantenimiento: Mantenimiento,
    color: Color,
    mostrarProgreso: Boolean,
    estadoTexto: String,
    onClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, color)
    ) {
        Column {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = color
            ) {
                Text(
                    text = estadoTexto,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.padding(16.dp).background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = mantenimiento.maquinaNombre ?: "Máquina #${mantenimiento.equipoId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "S/N: ${mantenimiento.numeroSerie ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tipo: ${mantenimiento.tipoMantenimiento?.nombre ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                mantenimiento.fechaInicio?.let {
                    Text(
                        text = "Fecha: ${formatearFecha(it)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (mostrarProgreso) {
                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { mantenimiento.progresoChecklist },
                        modifier = Modifier.fillMaxWidth(),
                        color = color
                    )

                    Text(
                        text = "${(mantenimiento.progresoChecklist * 100).toInt()}% completado",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignarTecnicoModal(
    mantenimiento: Mantenimiento,
    tecnicos: List<Usuario>,
    onDismiss: () -> Unit,
    onAsignar: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var tecnicoSeleccionado by remember { mutableStateOf<Usuario?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Asignar técnico",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Mantenimiento #${mantenimiento.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = tecnicoSeleccionado?.email ?: "Seleccionar técnico",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        tecnicos.forEach { tecnico ->
                            DropdownMenuItem(
                                text = { Text(tecnico.email) },
                                onClick = {
                                    tecnicoSeleccionado = tecnico
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                MaintixButton(
                    onClick = {
                        tecnicoSeleccionado?.let { onAsignar(it.id) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = tecnicoSeleccionado != null
                ) {
                    Text("Asignar")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

fun formatearFecha(fecha: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
        val date = inputFormat.parse(fecha)
        date?.let { outputFormat.format(it) } ?: fecha
    } catch (e: Exception) {
        fecha
    }
}