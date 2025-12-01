package com.tonhete.maintixapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.tonhete.maintixapp.ui.theme.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.LoginRequest
import kotlinx.coroutines.launch
import com.tonhete.maintixapp.data.appState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.layout.ContentScale
import com.tonhete.maintixapp.R
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import com.tonhete.maintixapp.ui.components.MaintixButton

@Composable
fun LoginScreen(
    onLoginSuccess: (tipoUsuarioId: Int) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = OrangeMain,
            background = Color(0xFF0D0D0D),
            surface = Color(0xFF252525),
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = rememberAsyncImagePainter(R.drawable.login_img2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Capa semi-transparente (opcional, para que se lea mejor el texto)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            // Contenido del login
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                /*Text(
                text = "MAINTIX",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface // Texto blanco
            )*/
                //Spacer(modifier = Modifier.height(48.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = TextPlaceholder) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña", color = TextPlaceholder) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                errorMessage?.let {
                    Surface(
                        color = MaterialTheme.colorScheme.onSurface

                    ) {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                MaintixButton(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Completa todos los campos"
                            return@MaintixButton
                        }

                        scope.launch {
                            isLoading = true
                            errorMessage = null

                            try {
                                val response = RetrofitClient.apiService.login(
                                    LoginRequest(email = email, password = password)
                                )

                                if (response.isSuccessful) {
                                    val loginResponse = response.body()
                                    if (loginResponse != null) {
                                        appState.tecnicoId = loginResponse.usuarioId
                                        onLoginSuccess(loginResponse.tipoUsuarioId)
                                        RetrofitClient.saveToken(loginResponse.token)
                                    } else {
                                        errorMessage = "Error: respuesta vacía"
                                    }
                                } else {
                                    errorMessage = when (response.code()) {
                                        401 -> "Credenciales incorrectas"
                                        else -> "Error: ${response.code()}"
                                    }
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error de conexión: ${e.message}"
                                Log.e("LoginScreen", "Error:", e)
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            "INICIAR SESIÓN",
                            color = Color.Black
                        )

                    }
                }
            }
        }
    }
}