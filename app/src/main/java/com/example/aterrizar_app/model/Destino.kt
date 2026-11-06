package com.example.aterrizar_app.model

import com.example.aterrizar_app.util.formatoPrecio

data class Destino(
    val id: Int,
    val nombre: String,
    val pais: String,
    val descripcion: String,
    val dias: Int,
    val noches: Int,
    val precio: Int,
    val categoria: Categoria,
    val imagen: Int
) {
    val duracion: String
        get() = "$dias días / $noches noches"

    val precioTexto: String
        get() = formatoPrecio(precio)
}