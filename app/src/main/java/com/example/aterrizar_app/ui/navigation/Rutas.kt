package com.example.aterrizar_app.ui.navigation

object Rutas {
    const val INICIO = "inicio"
    const val FAVORITOS = "favoritos"
    const val MIS_VIAJES = "mis_viajes"
    const val RESERVAR = "reservar/{destinoId}"

    fun reservarConId(id: Int) = "reservar/$id"
}

data class ItemBarra(val ruta: String, val etiqueta: String, val emoji: String)

val itemsBarra = listOf(
    ItemBarra(Rutas.INICIO, "Inicio", "🔍"),
    ItemBarra(Rutas.FAVORITOS, "Favoritos", "❤️"),
    ItemBarra(Rutas.MIS_VIAJES, "Mis viajes", "✈️")
)