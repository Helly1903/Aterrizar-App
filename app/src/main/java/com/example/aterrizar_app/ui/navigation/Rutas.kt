package com.example.aterrizar_app.ui.navigation

object Rutas {
    const val INICIO = "inicio"
    const val FAVORITOS = "favoritos"
    const val MIS_VIAJES = "mis_viajes"

    // Ruta con argumento: {destinoId} se reemplaza por el id del destino
    const val RESERVAR = "reservar/{destinoId}"

    fun reservarConId(destinoId: Int) = "reservar/$destinoId"
}

data class ItemBarra(
    val ruta: String,
    val emoji: String,
    val etiqueta: String
)

val itemsBarra = listOf(
    ItemBarra(Rutas.INICIO, "🏠", "Inicio"),
    ItemBarra(Rutas.FAVORITOS, "❤️", "Favoritos"),
    ItemBarra(Rutas.MIS_VIAJES, "🧳", "Mis viajes")
)