package com.example.aterrizar_app.util

import com.example.aterrizar_app.model.Cotizacion
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import kotlin.math.roundToInt

// El niño paga 50%, el plan multiplica el precio,
// y desde 4 viajeros hay 10% de descuento sobre el subtotal.
fun calcularCotizacion(
    destino: Destino,
    plan: Plan,
    adultos: Int,
    ninos: Int
): Cotizacion {
    val personasEquivalentes = adultos + ninos * 0.5
    val subtotal = (destino.precio * plan.multiplicador * personasEquivalentes).roundToInt()
    val descuento = if (adultos + ninos >= 4) (subtotal * 0.10).roundToInt() else 0
    return Cotizacion(
        subtotal = subtotal,
        descuento = descuento,
        total = subtotal - descuento
    )
}