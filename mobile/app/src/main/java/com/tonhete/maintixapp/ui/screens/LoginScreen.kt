package com.tonhete.maintixapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tonhete.maintixapp.data.RetrofitClient
import com.tonhete.maintixapp.data.models.LoginRequest
import kotlinx.coroutines.launch
import com.tonhete.maintixapp.data.appState

@Composable
fun LoginScreen(
    onLoginSuccess: (tipoUsuarioId: Int) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MAINTIX",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Completa todos los campos"
                    return@Button
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
                Text("INICIAR SESIÓN")
            }
        }
    }
}