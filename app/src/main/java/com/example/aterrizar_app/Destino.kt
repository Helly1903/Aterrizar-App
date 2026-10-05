package com.example.aterrizar_app

import androidx.compose.ui.graphics.Color
import com.example.aterrizar_app.ui.theme.MoradoCiudad
import com.example.aterrizar_app.ui.theme.OcreHistoria
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.ui.theme.VerdeMontana

data class Destino(
    val nombre: String,
    val pais: String,
    val descripcion: String,
    val duracion: String,
    val precio: String,
    val categoria: String,
    val colorCategoria: Color,
    val imagen: Int
)

val destinos = listOf(
    Destino(
        nombre = "Cartagena",
        pais = "Colombia",
        descripcion = "Murallas históricas, calles coloridas y playas del Caribe.",
        duracion = "5 días / 4 noches",
        precio = "$1.850.000",
        categoria = "🏛️ Historia",
        colorCategoria = OcreHistoria,
        imagen = R.drawable.cartagena
    ),
    Destino(
        nombre = "Cancún",
        pais = "México",
        descripcion = "Arena blanca, mar turquesa y vida nocturna inolvidable.",
        duracion = "6 días / 5 noches",
        precio = "$3.200.000",
        categoria = "🏖️ Playa",
        colorCategoria = Turquesa,
        imagen = R.drawable.cancun
    ),
    Destino(
        nombre = "Machu Picchu",
        pais = "Perú",
        descripcion = "La ciudadela inca entre las nubes, una maravilla del mundo.",
        duracion = "5 días / 4 noches",
        precio = "$3.900.000",
        categoria = "🏛️ Historia",
        colorCategoria = OcreHistoria,
        imagen = R.drawable.machupicchu
    ),
    Destino(
        nombre = "Roma",
        pais = "Italia",
        descripcion = "El Coliseo, la Fontana di Trevi y la mejor pasta del mundo.",
        duracion = "8 días / 7 noches",
        precio = "$6.500.000",
        categoria = "🏛️ Historia",
        colorCategoria = OcreHistoria,
        imagen = R.drawable.roma
    ),
    Destino(
        nombre = "París",
        pais = "Francia",
        descripcion = "La Torre Eiffel, museos legendarios y cafés con encanto.",
        duracion = "7 días / 6 noches",
        precio = "$6.900.000",
        categoria = "🌆 Ciudad",
        colorCategoria = MoradoCiudad,
        imagen = R.drawable.paris
    ),
    Destino(
        nombre = "Santorini",
        pais = "Grecia",
        descripcion = "Casas blancas, cúpulas azules y atardeceres de postal.",
        duracion = "7 días / 6 noches",
        precio = "$7.200.000",
        categoria = "🏖️ Playa",
        colorCategoria = Turquesa,
        imagen = R.drawable.santorini
    ),
    Destino(
        nombre = "Río de Janeiro",
        pais = "Brasil",
        descripcion = "Copacabana, el Cristo Redentor y mucha samba.",
        duracion = "6 días / 5 noches",
        precio = "$4.600.000",
        categoria = "🏖️ Playa",
        colorCategoria = Turquesa,
        imagen = R.drawable.rio
    ),
    Destino(
        nombre = "Patagonia",
        pais = "Argentina",
        descripcion = "Glaciares, lagos y montañas para los amantes de la aventura.",
        duracion = "8 días / 7 noches",
        precio = "$5.400.000",
        categoria = "🏔️ Montaña",
        colorCategoria = VerdeMontana,
        imagen = R.drawable.patagonia
    )
)
