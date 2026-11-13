package com.example.aterrizar_app.ui.navigation

object Rutas {
    const val INICIO = "inicio"
    const val FAVORITOS = "favoritos"
    const val MIS_VIAJES = "mis_viajes"
    const val RESERVAR = "reservar/{destinoId}"
    const val EDITAR_RESERVA = "editar_reserva/{reservaId}"
    const val DETALLE_VIAJE = "detalle_viaje/{reservaId}"

    fun reservarConId(id: Int) = "reservar/$id"
    fun editarReservaConId(id: Int) = "editar_reserva/$id"
    fun detalleViajeConId(id: Int) = "detalle_viaje/$id"
}

data class ItemBarra(val ruta: String, val etiqueta: String, val emoji: String)

val itemsBarra = listOf(
    ItemBarra(Rutas.INICIO, "Inicio", "🔍"),
    ItemBarra(Rutas.FAVORITOS, "Favoritos", "❤️"),
    ItemBarra(Rutas.MIS_VIAJES, "Mis viajes", "✈️")
)