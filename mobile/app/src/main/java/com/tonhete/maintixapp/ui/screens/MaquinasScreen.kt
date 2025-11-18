package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.Equipo
import com.tonhete.maintixapp.data.models.Mantenimiento
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaquinasScreen(navController: NavController) {
    var equipos by remember { mutableStateOf<List<Equipo>>(emptyList()) }
    var mantenimientos by remember { mutableStateOf<List<Mantenimiento>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Cargar datos al inicio
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val equiposResponse = RetrofitClient.apiService.getEquipos()
                val mantenimientosResponse = RetrofitClient.apiService.getMantenimientos()

                if (equiposResponse.isSuccessful) {
                    equipos = equiposResponse.body() ?: emptyList()
                }
                if (mantenimientosResponse.isSuccessful) {
                    mantenimientos = mantenimientosResponse.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    // Filtrar equipos por búsqueda
    val equiposFiltrados = equipos.filter {
        val nombreMaquina = it.tipoMaquinaria?.descripcion ?: ""
        val numeroSerie = it.numeroSerie ?: ""
        nombreMaquina.contains(searchText, ignoreCase = true) ||
                numeroSerie.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Máquinas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Buscador
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Buscar máquina...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        // Lista de equipos
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (equiposFiltrados.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron máquinas")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(equiposFiltrados) { equipo ->
                    EquipoCard(
                        equipo = equipo,
                        mantenimientos = mantenimientos.filter { it.equipoId == equipo.id },
                        onClick = {
                            navController.navigate("detalle_equipo/${equipo.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EquipoCard(
    equipo: Equipo,
    mantenimientos: List<Mantenimiento>,
    onClick: () -> Unit
) {
    // Filtrar mantenimientos pendientes (no finalizados)
    val mantenimientosPendientes = mantenimientos.filter {
        it.estado != "finalizado"
    }

    // Agrupar por tipo de mantenimiento
    val tiposPendientes = mantenimientosPendientes
        .mapNotNull { it.tipoMantenimiento?.nombre }
        .distinct()
        .joinToString(", ")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Nombre de la máquina
            Text(
                text = equipo.tipoMaquinaria?.descripcion ?: "Sin nombre",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Número de serie
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "S/N: ${equipo.numeroSerie ?: "N/A"}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Horas: ${equipo.horasActuales ?: 0}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Mantenimientos pendientes
            if (mantenimientosPendientes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFFFF3CD)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pendientes: ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF856404)
                        )
                        Text(
                            text = tiposPendientes.ifEmpty { "Sin especificar" },
                            fontSize = 12.sp,
                            color = Color(0xFF856404)
                        )
                    }
                }
            }
        }
    }
}