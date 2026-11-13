package com.example.aterrizar_app.model

// Reserva confirmada por el titular
data class Reserva(
    val id: Int,
    val destino: Destino,
    val nombre: String,
    val correo: String,
    val adultos: Int,
    val ninos: Int,
    val plan: Plan,
    val total: Int,
    val fechaViaje: String = "",
    val notas: String = ""
)