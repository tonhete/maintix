package com.tonhete.maintixapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.tonhete.maintixapp.ui.navigation.AppNavigation
import com.tonhete.maintixapp.ui.theme.MaintixAppTheme

// Actividad principal: punto de entrada de la app
class MainActivity : ComponentActivity() {

    // Se ejecuta cuando se crea la actividad (al abrir la app)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: define qué se muestra en pantalla
        setContent {

            // MaintixAppTheme: aplica el tema visual (colores, fuentes...)
            MaintixAppTheme {

                // AppNavigation: arranca el sistema de navegación
                // Esto muestra el NavHost con todas las pantallas
                AppNavigation()
            }
        }
    }
}