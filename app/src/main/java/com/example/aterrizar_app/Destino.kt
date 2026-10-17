package com.example.aterrizar_app

import androidx.compose.ui.graphics.Color
import com.example.aterrizar_app.ui.theme.MoradoCiudad
import com.example.aterrizar_app.ui.theme.OcreHistoria
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.ui.theme.VerdeMontana
import java.util.Locale
import kotlin.math.roundToInt

enum class Categoria(val etiqueta: String, val color: Color) {
    PLAYA("🏖️ Playa", Turquesa),
    HISTORIA("🏛️ Historia", OcreHistoria),
    MONTANA("🏔️ Montaña", VerdeMontana),
    CIUDAD("🌆 Ciudad", MoradoCiudad)
}

// Plan de viaje: el multiplicador cambia el precio base
enum class Plan(val etiqueta: String, val multiplicador: Double) {
    BASICO("Básico", 1.0),
    CONFORT("Confort", 1.25),
    PREMIUM("Premium", 1.5)
}

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

// Reserva confirmada por el titular
data class Reserva(
    val id: Int,
    val destino: Destino,
    val nombre: String,
    val correo: String,
    val adultos: Int,
    val ninos: Int,
    val plan: Plan,
    val total: Int
)

// Resultado del cálculo del precio
data class Cotizacion(
    val subtotal: Int,
    val descuento: Int,
    val total: Int
)

// Convierte 1850000 en "$1.850.000"
fun formatoPrecio(valor: Int): String =
    "\$" + String.format(Locale.forLanguageTag("es-CO"), "%,d", valor)

// el niño paga 50 %, el plan multiplica el precio,
// y desde 4 viajeros hay 10 % de descuento sobre el subtotal.
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

val destinos = listOf(
    Destino(1, "Cartagena", "Colombia", "Murallas históricas, calles coloridas y playas del Caribe.", 5, 4, 1850000, Categoria.HISTORIA, R.drawable.cartagena),
    Destino(2, "Cancún", "México", "Arena blanca, mar turquesa y vida nocturna inolvidable.", 6, 5, 3200000, Categoria.PLAYA, R.drawable.cancun),
    Destino(3, "Machu Picchu", "Perú", "La ciudadela inca entre las nubes, una maravilla del mundo.", 5, 4, 3900000, Categoria.HISTORIA, R.drawable.machupicchu),
    Destino(4, "Roma", "Italia", "El Coliseo, la Fontana di Trevi y la mejor pasta del mundo.", 8, 7, 6500000, Categoria.HISTORIA, R.drawable.roma),
    Destino(5, "París", "Francia", "La Torre Eiffel, museos legendarios y cafés con encanto.", 7, 6, 6900000, Categoria.CIUDAD, R.drawable.paris),
    Destino(6, "Santorini", "Grecia", "Casas blancas, cúpulas azules y atardeceres de postal.", 7, 6, 7200000, Categoria.PLAYA, R.drawable.santorini),
    Destino(7, "Río de Janeiro", "Brasil", "Copacabana, el Cristo Redentor y mucha samba.", 6, 5, 4600000, Categoria.PLAYA, R.drawable.rio),
    Destino(8, "Patagonia", "Argentina", "Glaciares, lagos y montañas para los amantes de la aventura.", 8, 7, 5400000, Categoria.MONTANA, R.drawable.patagonia)
)