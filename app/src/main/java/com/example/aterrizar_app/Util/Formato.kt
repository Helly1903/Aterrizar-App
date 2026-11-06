package com.example.aterrizar_app.util

import java.text.Normalizer
import java.util.Locale

// Convierte 1850000 en "$1.850.000"
fun formatoPrecio(valor: Int): String =
    "$" + String.format(Locale.forLanguageTag("es-CO"), "%,d", valor)

// Quita tildes para que "paris" encuentre "Paris"
fun String.sinTildes(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("\\p{M}"), "")

// "1 adulto" / "2 adultos 1 niño"
fun textoViajeros(adultos: Int, ninos: Int): String {
    val textoAdultos = if (adultos == 1) "1 adulto" else "$adultos adultos"
    if (ninos == 0) return textoAdultos
    val textoNinos = if (ninos == 1) "1 niño" else "$ninos niños"
    return "$textoAdultos $textoNinos"
}