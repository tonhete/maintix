package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.appState
import com.tonhete.maintixapp.data.models.ChecklistItem
import com.tonhete.maintixapp.data.models.ActualizarChecklistDto
import com.tonhete.maintixapp.data.models.ActualizarItemChecklistDto
import com.tonhete.maintixapp.data.models.FinalizarMantenimientoDto
import com.tonhete.maintixapp.data.models.Mantenimiento
import com.tonhete.maintixapp.ui.components.ItemDetalleModal
import com.tonhete.maintixapp.ui.components.MaintixButton
import kotlinx.coroutines.launch

@Composable
fun ChecklistScreen(
    mantenimientoId: Int,
    navController: NavController,
    soloLectura: Boolean = false
) {
    // Estados de la pantalla
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<ChecklistItem?>(null) }
    var mantenimiento by remember { mutableStateOf<Mantenimiento?>(null) }

    val scope = rememberCoroutineScope()

    // Cargar checklist al iniciar
    LaunchedEffect(mantenimientoId) {
        scope.launch {
            try {
                val checklistResponse = RetrofitClient.apiService.getChecklistMantenimiento(mantenimientoId)
                if (checklistResponse.isSuccessful) {
                    checklistItems = checklistResponse.body()?.items ?: emptyList()
                } else {
                    errorMessage = "Error: ${checklistResponse.code()}"
                }

                // Cargar datos del mantenimiento
                val mantResponse = RetrofitClient.apiService.getMantenimiento(mantenimientoId)
                if (mantResponse.isSuccessful) {
                    mantenimiento = mantResponse.body()
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Función para guardar progreso (sin finalizar)
    fun guardarProgreso() {
        scope.launch {
            isSaving = true
            errorMessage = null
            successMessage = null

            try {
                val dto = ActualizarChecklistDto(
                    items = checklistItems.map {
                        ActualizarItemChecklistDto(
                            checklistId = it.checklistId,
                            completado = it.completado,
                            observaciones = it.observaciones
                        )
                    }
                )

                val response = RetrofitClient.apiService.actualizarChecklist(
                    mantenimientoId = mantenimientoId,
                    dto = dto
                )

                if (response.isSuccessful) {
                    successMessage = "Progreso guardado correctamente"
                } else {
                    errorMessage = "Error al guardar: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isSaving = false
            }
        }
    }

    // Función para finalizar mantenimiento (solo si todo completo)
    fun finalizarMantenimiento() {
        scope.launch {
            isSaving = true
            errorMessage = null

            try {
                // 1. Guardar checklist
                val checklistDto = ActualizarChecklistDto(
                    items = checklistItems.map {
                        ActualizarItemChecklistDto(
                            checklistId = it.checklistId,
                            completado = it.completado,
                            observaciones = it.observaciones
                        )
                    }
                )

                val checklistResponse = RetrofitClient.apiService.actualizarChecklist(
                    mantenimientoId = mantenimientoId,
                    dto = checklistDto
                )

                if (checklistResponse.isSuccessful) {
                    // 2. Finalizar mantenimiento
                    val finalizarDto = FinalizarMantenimientoDto(
                        usuarioId = 1, // TODO: usar usuario real del login
                        incidencias = null // TODO: permitir añadir incidencias
                    )

                    val finalizarResponse = RetrofitClient.apiService.finalizarMantenimiento(
                        mantenimientoId = mantenimientoId,
                        dto = finalizarDto
                    )

                    if (finalizarResponse.isSuccessful) {
                        // 3. Limpiar estado y volver al dashboard
                        appState.finalizarMantenimiento()
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = false }
                        }
                    } else {
                        errorMessage = "Error al finalizar: ${finalizarResponse.code()}"
                    }
                } else {
                    errorMessage = "Error al guardar checklist: ${checklistResponse.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isSaving = false
            }
        }
    }

    // Calcular progreso
    val completados = checklistItems.count { it.completado }
    val total = checklistItems.size
    val todoCompleto = total > 0 && completados == total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón volver
        //TextButton(onClick = { navController.popBackStack() }) {
        //    Text("← Volver")
        //}

        // Título
        Column {
            Text(
                text = "${mantenimiento?.tipoMaquinariaInfo?.descripcion?.substringBefore("(")?.trim() ?: "Mantenimiento"} #$mantenimientoId",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = mantenimiento?.tipoMantenimiento?.descripcion ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            // Estado: Cargando
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // Estado: Error
            errorMessage != null -> {
                Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
            // Estado: Lista vacía
            checklistItems.isEmpty() -> {
                Text("No hay items en este checklist")
            }
            // Estado: Lista cargada
            else -> {
                // Mensaje de éxito (si se guardó)
                if (!soloLectura) {
                    successMessage?.let {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = it,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Progreso
                Text(
                    text = "$completados/$total items completados",
                    style = MaterialTheme.typography.bodyLarge
                )
                LinearProgressIndicator(
                    progress = { if (total > 0) completados.toFloat() / total else 0f },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Lista de items
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(checklistItems) { item ->
                        CheckItemCard(
                            item = item,
                            soloLectura = soloLectura,
                            onCheckChange = { checked ->
                                if (!soloLectura) {
                                    // Actualizar estado local del checkbox
                                    checklistItems = checklistItems.map {
                                        if (it.checklistId == item.checklistId) {
                                            it.copy(completado = checked)
                                        } else it
                                    }
                                    successMessage = null
                                }
                            },
                            onClick = { selectedItem = item }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones según modo
                if (soloLectura) {
                    // Modo solo lectura (admin viendo histórico)
                    MaintixButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("VOLVER")
                    }
                } else {
                    // Modo normal (técnico trabajando)
                    Button(
                        onClick = { guardarProgreso() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        } else {
                            Text("GUARDAR PROGRESO")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { finalizarMantenimiento() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = todoCompleto && !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("FINALIZAR MANTENIMIENTO")
                        }
                    }
                }
            }
        }
    }

    // Modal de detalle de item (si hay uno seleccionado)
    selectedItem?.let { item ->
        ItemDetalleModal(
            item = item,
            onDismiss = { selectedItem = null }
        )
    }
}

// Card de cada item del checklist (clickeable)
@Composable
fun CheckItemCard(
    item: ChecklistItem,
    soloLectura: Boolean = false,
    onCheckChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Texto del item
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
                item.observaciones?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Checkbox (solo interactivo si NO es solo lectura)
            Checkbox(
                checked = item.completado,
                onCheckedChange = if (soloLectura) null else onCheckChange,
                enabled = !soloLectura
            )
        }
    }
}