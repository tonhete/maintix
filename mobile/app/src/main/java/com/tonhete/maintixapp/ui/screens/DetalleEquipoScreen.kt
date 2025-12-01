package com.tonhete.maintixapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.*
import com.tonhete.maintixapp.ui.components.MaintixButton
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEquipoScreen(
    equipoId: Int,
    navController: NavController
) {
    var equipo by remember { mutableStateOf<Equipo?>(null) }
    var mantenimientos by remember { mutableStateOf<List<Mantenimiento>>(emptyList()) }
    var historicos by remember { mutableStateOf<List<Historico>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showActualizarHorasModal by remember { mutableStateOf(false) }
    var historicoExpandido by remember { mutableStateOf(false) }
    var historicoSeleccionado by remember { mutableStateOf<Historico?>(null) }
    val scope = rememberCoroutineScope()
    var proveedor by remember { mutableStateOf<Proveedor?>(null) }
    var proveedorExpandido by remember { mutableStateOf(false) }

    // Cargar datos
    LaunchedEffect(equipoId) {
        scope.launch {
            try {
                val equipoResponse = RetrofitClient.apiService.getEquipo(equipoId)
                val mantenimientosResponse = RetrofitClient.apiService.getMantenimientos()
                val historicosResponse = RetrofitClient.apiService.getHistoricoEquipo(equipoId)

                if (equipoResponse.isSuccessful) {
                    equipo = equipoResponse.body()


                    val proveedorId = equipo?.tipoMaquinaria?.proveedorId
                    if (proveedorId != null) {
                        val proveedorResponse = RetrofitClient.apiService.getProveedor(proveedorId)
                        if (proveedorResponse.isSuccessful) {
                            proveedor = proveedorResponse.body()
                        }
                    }
                }
                if (mantenimientosResponse.isSuccessful) {
                    mantenimientos = mantenimientosResponse.body()?.filter {
                        it.equipoId == equipoId && it.estado != "finalizado"
                    } ?: emptyList()
                }
                if (historicosResponse.isSuccessful) {
                    historicos = historicosResponse.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header con botón volver
            /*
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                    Text(
                        text = "Detalle del Equipo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }*/

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Información del equipo
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "INFORMACIÓN DEL EQUIPO",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            InfoRow("Nombre", equipo?.tipoMaquinaria?.descripcion ?: "N/A")
                            InfoRow("S/N", equipo?.numeroSerie ?: "N/A")
                            InfoRow("Horas", "${equipo?.horasActuales ?: 0}")
                            InfoRow("Fabricación", equipo?.fechaFabricacion ?: "N/A")

                            Spacer(modifier = Modifier.height(12.dp))

                            MaintixButton(
                                onClick = { showActualizarHorasModal = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Edit, "Actualizar", modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Actualizar Horas")
                            }
                        }
                    }
                }

                // Mantenimientos pendientes
                item {
                    Text(
                        text = "MANTENIMIENTOS PENDIENTES",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                if (mantenimientos.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No hay mantenimientos pendientes", color = Color.Gray)
                            }
                        }
                    }
                } else {
                    items(mantenimientos) { mant ->
                        MantenimientoMiniCard(
                            mantenimiento = mant,
                            onClick = {
                                navController.navigate("admin/checklist/${mant.id}")
                            }
                        )
                    }
                }
                // Proveedor (desplegable)
                item {
                    if (proveedor != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                // Header clickeable
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { proveedorExpandido = !proveedorExpandido }
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "PROVEEDOR",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                    Icon(
                                        imageVector = if (proveedorExpandido) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Expandir/Contraer"
                                    )
                                }

                                // Contenido desplegable
                                AnimatedVisibility(
                                    visible = proveedorExpandido,
                                    enter = expandVertically(),
                                    exit = shrinkVertically()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        InfoRow("Nombre", proveedor?.nombre ?: "N/A")
                                        InfoRow("Teléfono", proveedor?.tlf ?: "N/A")
                                        InfoRow("Dirección", proveedor?.direccion ?: "N/A")
                                    }
                                }
                            }
                        }
                    }
                }

                // Histórico (desplegable)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Header clickeable
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { historicoExpandido = !historicoExpandido }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "HISTÓRICO (${historicos.size})",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                                Icon(
                                    imageVector = if (historicoExpandido) Icons.Default.KeyboardArrowUp
                                    else Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Expandir/Contraer"
                                )
                            }

                            // Contenido desplegable
                            AnimatedVisibility(
                                visible = historicoExpandido,
                                enter = expandVertically(),
                                exit = shrinkVertically()
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (historicos.isEmpty()) {
                                        Text(
                                            text = "No hay histórico disponible",
                                            color = Color.Gray,
                                            modifier = Modifier.padding(vertical = 16.dp)
                                        )
                                    } else {
                                        historicos.forEach { hist ->
                                            HistoricoMiniCard(
                                                historico = hist,
                                                onClick = { historicoSeleccionado = hist }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal actualizar horas
    if (showActualizarHorasModal) {
        ActualizarHorasModal(
            horasActuales = equipo?.horasActuales ?: 0,
            onDismiss = { showActualizarHorasModal = false },
            onConfirm = { nuevasHoras ->
                scope.launch {
                    try {
                        val response = RetrofitClient.apiService.actualizarHorasEquipo(
                            equipoId,
                            ActualizarHorasDto(nuevasHoras)
                        )
                        if (response.isSuccessful) {
                            // Recargar equipo
                            val equipoResponse = RetrofitClient.apiService.getEquipo(equipoId)
                            if (equipoResponse.isSuccessful) {
                                equipo = equipoResponse.body()
                            }
                            showActualizarHorasModal = false
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )
    }

    // Modal detalle histórico
    if (historicoSeleccionado != null) {
        DetalleHistoricoModal(
            historico = historicoSeleccionado!!,
            onDismiss = { historicoSeleccionado = null }
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Text(text = value, fontSize = 14.sp, color = Color.Gray)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MantenimientoMiniCard(
    mantenimiento: Mantenimiento,
    onClick: () -> Unit
) {
    val colorBarra = when (mantenimiento.estado) {
        "pendiente_asignacion" -> Color(0xFF2196F3) // Azul
        "pendiente" -> Color(0xFFFF9800) // Naranja
        "en_progreso" -> Color(0xFFFF9800)
        else -> Color(0xFF4CAF50) // Verde
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Barra superior de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(colorBarra)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mantenimiento #${mantenimiento.id}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mantenimiento.tipoMantenimiento?.nombre ?: "N/A",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatearFecha(mantenimiento.fechaInicio),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HistoricoMiniCard(
    historico: Historico,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)

    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(MaterialTheme.colorScheme.background)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mantenimiento #${historico.id}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tipo ${historico.clase}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatearFecha(historico.fechaFinalizacion),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "[FINALIZADO]",
                    fontSize = 11.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ActualizarHorasModal(
    horasActuales: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var nuevasHoras by remember { mutableStateOf(horasActuales.toString()) }
    var error by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Actualizar Horas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Horas actuales: $horasActuales",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = nuevasHoras,
                    onValueChange = {
                        nuevasHoras = it
                        error = it.toIntOrNull() == null
                    },
                    label = { Text("Nuevas horas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = error,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                )

                if (error) {
                    Text(
                        text = "Introduce un número válido",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MaintixButton(
                    onClick = {
                        val horas = nuevasHoras.toIntOrNull()
                        if (horas != null && horas >= 0) {
                            onConfirm(horas)
                        } else {
                            error = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !error && nuevasHoras.isNotEmpty()
                ) {
                    Text("Guardar")
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

@Composable
fun DetalleHistoricoModal(
    historico: Historico,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Detalle del Histórico #${historico.id}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow("Tipo", "Clase ${historico.clase}")
                    InfoRow("Operario", historico.operario)
                    InfoRow("Horas máquina", "${historico.horasMaquina}")
                    InfoRow("Fecha inicio", formatearFecha(historico.fechaMantenimiento))
                    InfoRow("Fecha fin", formatearFecha(historico.fechaFinalizacion))

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Incidencias:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = historico.incidencias ?: "Sin incidencias",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MaintixButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatearFecha(fecha: String?): String {
    if (fecha.isNullOrEmpty()) return "N/A"
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(fecha, DateTimeFormatter.ISO_DATE_TIME)
        dateTime.format(formatter)
    } catch (e: Exception) {
        fecha
    }
}