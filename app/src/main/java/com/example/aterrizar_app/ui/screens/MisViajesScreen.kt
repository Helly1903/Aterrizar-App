package com.example.aterrizar_app.ui.screens

import androidx.compose.runtime.Composable
import com.example.aterrizar_app.ui.components.EstadoVacio

@Composable
fun MisViajesScreen() {
    EstadoVacio(
        emoji = "🧳",
        titulo = "Tus viajes",
        mensaje = "Aquí verás los viajes que reserves, ¿A dónde vamos?."
    )
}