package com.example.aterrizar_app.ui.screens

import androidx.compose.runtime.Composable
import com.example.aterrizar_app.ui.components.EstadoVacio

@Composable
fun FavoritosScreen() {
    EstadoVacio(
        emoji = "❤️",
        titulo = "Tus favoritos",
        mensaje = "Aquí verás los destinos que marcarán tu corazón."
    )
}