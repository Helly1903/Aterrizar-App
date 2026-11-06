package com.example.aterrizar_app.model

// Resultado del cálculo del precio
data class Cotizacion(
    val subtotal: Int,
    val descuento: Int,
    val total: Int
)