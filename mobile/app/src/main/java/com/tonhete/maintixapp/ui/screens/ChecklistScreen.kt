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
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.appState
import com.tonhete.maintixapp.data.models.ChecklistItem
import com.tonhete.maintixapp.data.models.ActualizarChecklistDto
import com.tonhete.maintixapp.data.models.ActualizarItemChecklistDto
import com.tonhete.maintixapp.data.models.FinalizarMantenimientoDto
import com.tonhete.maintixapp.ui.components.ItemDetalleModal
import kotlinx.coroutines.launch

@Composable
fun ChecklistScreen(
    mantenimientoId: Int,
    navController: NavController
) {
    // Estados de la pantalla
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<ChecklistItem?>(null) } // Item seleccionado para modal

    val scope = rememberCoroutineScope()

    // Cargar checklist al iniciar
    LaunchedEffect(mantenimientoId) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getChecklistMantenimiento(mantenimientoId)
                if (response.isSuccessful) {
                    checklistItems = response.body()?.items ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
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
        TextButton(onClick = { navController.popBackStack() }) {
            Text("← Volver")
        }

        // Título
        Text(
            text = "Checklist Mantenimiento #$mantenimientoId",
            style = MaterialTheme.typography.headlineMedium
        )

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
                            onCheckChange = { checked ->
                                // Actualizar estado local del checkbox
                                checklistItems = checklistItems.map {
                                    if (it.checklistId == item.checklistId) {
                                        it.copy(completado = checked)
                                    } else it
                                }
                                successMessage = null // Limpiar mensaje al hacer cambios
                            },
                            onClick = { selectedItem = item } // Abrir modal al pulsar
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón GUARDAR PROGRESO (siempre activo)
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

                // Botón FINALIZAR (solo si todo completo)
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

    // Modal de detalle de item (si hay uno seleccionado)
    selectedItem?.let { item ->
        ItemDetalleModal(
            item = item,
            onDismiss = { selectedItem = null } // Cerrar modal
        )
    }
}

// Card de cada item del checklist (clickeable)
@Composable
fun CheckItemCard(
    item: ChecklistItem,
    onCheckChange: (Boolean) -> Unit,
    onClick: () -> Unit // Callback al pulsar la card
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Hacer clickeable toda la card
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

            // Checkbox
            Checkbox(
                checked = item.completado,
                onCheckedChange = onCheckChange
            )
        }
    }
}