package com.example.aterrizar_app.model

import androidx.compose.ui.graphics.Color
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.OcreHistoria
import com.example.aterrizar_app.ui.theme.VerdeMontana

enum class EstadoReserva(val etiqueta: String, val color: Color) {
    PENDIENTE("⏳ Pendiente", OcreHistoria),
    CONFIRMADA("✅ Confirmada", VerdeMontana),
    COMPLETADA("🏁 Completada", AzulOceano)
}