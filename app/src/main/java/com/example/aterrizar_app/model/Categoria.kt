package com.example.aterrizar_app.model

import androidx.compose.ui.graphics.Color
import com.example.aterrizar_app.ui.theme.MoradoCiudad
import com.example.aterrizar_app.ui.theme.OcreHistoria
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.ui.theme.VerdeMontana

enum class Categoria(val etiqueta: String, val color: Color) {
    PLAYA("🏖️ Playa", Turquesa),
    HISTORIA("🏛️ Historia", OcreHistoria),
    MONTANA("⛰️ Montaña", VerdeMontana),
    CIUDAD("🏙️ Ciudad", MoradoCiudad)
}