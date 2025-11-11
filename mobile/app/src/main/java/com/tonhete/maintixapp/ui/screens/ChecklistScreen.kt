package com.tonhete.maintixapp.ui.screens

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
import kotlinx.coroutines.launch

@Composable
fun ChecklistScreen(
    mantenimientoId: Int,
    navController: NavController
) {
    var checklistItems by remember { mutableStateOf<List<ChecklistItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // Cargar checklist
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

    // Función para guardar progreso
    fun guardarProgreso() {
        scope.launch {
            isSaving = true
            errorMessage = null
            successMessage = null

            try {
                // Crear DTO para actualizar checklist
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

    // Función para finalizar mantenimiento
    fun finalizarMantenimiento() {
        scope.launch {
            isSaving = true
            errorMessage = null

            try {
                // Primero guardamos el checklist
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
                    // Luego finalizamos el mantenimiento
                    val finalizarDto = FinalizarMantenimientoDto(
                        usuarioId = 1, // TODO: usar usuario real del login
                        incidencias = null // TODO: permitir añadir incidencias
                    )

                    val finalizarResponse = RetrofitClient.apiService.finalizarMantenimiento(
                        mantenimientoId = mantenimientoId,
                        dto = finalizarDto
                    )

                    if (finalizarResponse.isSuccessful) {
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

    val completados = checklistItems.count { it.completado }
    val total = checklistItems.size
    val todoCompleto = total > 0 && completados == total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = { navController.popBackStack() }) {
            Text("← Volver")
        }

        Text(
            text = "Checklist Mantenimiento #$mantenimientoId",
            style = MaterialTheme.typography.headlineMedium
        )

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
            checklistItems.isEmpty() -> {
                Text("No hay items en este checklist")
            }
            else -> {
                // Mensaje de éxito
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

                Text(
                    text = "$completados/$total items completados",
                    style = MaterialTheme.typography.bodyLarge
                )
                LinearProgressIndicator(
                    progress = { if (total > 0) completados.toFloat() / total else 0f },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(checklistItems) { item ->
                        CheckItemCard(
                            item = item,
                            onCheckChange = { checked ->
                                checklistItems = checklistItems.map {
                                    if (it.checklistId == item.checklistId) {
                                        it.copy(completado = checked)
                                    } else it
                                }
                                successMessage = null // Limpiar mensaje al hacer cambios
                            }
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
}

@Composable
fun CheckItemCard(
    item: ChecklistItem,
    onCheckChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Checkbox(
                checked = item.completado,
                onCheckedChange = onCheckChange
            )
        }
    }
}