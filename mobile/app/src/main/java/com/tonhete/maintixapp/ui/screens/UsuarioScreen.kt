package com.tonhete.maintixapp.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.appState
import com.tonhete.maintixapp.data.models.Usuario
import kotlinx.coroutines.launch

@Composable
fun UsuarioScreen(navController: NavController) {
    val context = LocalContext.current
    val usuarioId = appState.tecnicoId ?: -1  // Usar appState en lugar de SharedPreferences

    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Cargar datos del usuario
    LaunchedEffect(usuarioId) {
        android.util.Log.d("UsuarioScreen", "UsuarioId recuperado: $usuarioId")

        if (usuarioId != -1) {
            scope.launch {
                try {
                    android.util.Log.d("UsuarioScreen", "Llamando a API: /api/Usuario/$usuarioId")
                    val response = RetrofitClient.apiService.getUsuario(usuarioId)

                    android.util.Log.d("UsuarioScreen", "Response code: ${response.code()}")

                    if (response.isSuccessful) {
                        usuario = response.body()
                        android.util.Log.d("UsuarioScreen", "Usuario cargado: ${usuario?.email}")
                    } else {
                        android.util.Log.e("UsuarioScreen", "Error: ${response.code()}")
                    }
                } catch (e: Exception) {
                    android.util.Log.e("UsuarioScreen", "Exception: ${e.message}", e)
                }
                isLoading = false
            }
        } else {
            android.util.Log.e("UsuarioScreen", "UsuarioId inválido: $usuarioId")
            isLoading = false
        }
    }

    // Diálogo de confirmación de logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Limpiar SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("MaintixPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            remove("token")
                            remove("usuarioId")
                            remove("tipoUsuarioId")
                            remove("email")
                            apply()
                        }

                        // Limpiar appState
                        appState.tecnicoId = null
                        appState.finalizarMantenimiento()

                        // Navegar al login
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                ) {
                    Text("Cerrar sesión", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título
        Text(
            text = "Mi Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (usuario == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se pudieron cargar los datos del usuario")
            }
        } else {
            // Card de información del usuario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Información de la cuenta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Email
                    UserInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = usuario!!.email
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    // Tipo de usuario
                    UserInfoRow(
                        icon = Icons.Default.Person,
                        label = "Rol",
                        value = when (usuario!!.tipoUsuarioId) {
                            1 -> "Administrador"
                            2 -> "Técnico"
                            else -> "Usuario"
                        }
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    // ID de usuario
                    UserInfoRow(
                        icon = Icons.Default.Person,
                        label = "ID de Usuario",
                        value = usuario!!.id.toString()
                    )
                }
            }

            // Botón de Cerrar Sesión
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC3545)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun UserInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}