package com.tonhete.maintixapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Pantalla de Login
// onLogin: función que se ejecuta cuando el usuario pulsa "Entrar"
@Composable
fun LoginScreen(onLogin: () -> Unit) {

    // Variables que guardan lo que escribe el usuario
    // remember: mantiene el valor aunque se redibuje la pantalla
    // mutableStateOf: cuando cambia, redibuja automáticamente
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Column: apila elementos verticalmente (uno debajo del otro)
    Column(
        modifier = Modifier
            .fillMaxSize()      // Ocupa toda la pantalla
            .padding(16.dp),    // Margen de 16dp alrededor
        horizontalAlignment = Alignment.CenterHorizontally,  // Centra horizontalmente
        verticalArrangement = Arrangement.Center             // Centra verticalmente
    ) {

        // Título "Login"
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge  // Letra grande
        )

        // Espacio vacío de 32dp
        Spacer(modifier = Modifier.height(32.dp))

        // Campo de texto para el usuario
        OutlinedTextField(
            value = usuario,                    // Muestra el valor de la variable
            onValueChange = { usuario = it },   // Actualiza la variable cuando escribes
            label = { Text("Usuario") }         // Etiqueta flotante
        )

        // Espacio vacío de 16dp
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )

        // Espacio vacío de 32dp
        Spacer(modifier = Modifier.height(32.dp))

        // Botón que ejecuta la función onLogin al pulsarlo
        Button(onClick = onLogin) {
            Text("Entrar")
        }
    }
}