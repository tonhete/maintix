package com.tonhete.maintixapp.data

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// Estado global de la app
// Guarda el ID del mantenimiento activo (si hay alguno)
class AppState {
    var mantenimientoEnCurso: String? by mutableStateOf(null)

    var tecnicoId: Int? by mutableStateOf(null)

    // Inicia un mantenimiento
    fun iniciarMantenimiento(id: String) {
        mantenimientoEnCurso = id
    }

    // Finaliza el mantenimiento actual
    fun finalizarMantenimiento() {
        mantenimientoEnCurso = null
    }
}

// Instancia global (simple, para el MVP)
val appState = AppState()