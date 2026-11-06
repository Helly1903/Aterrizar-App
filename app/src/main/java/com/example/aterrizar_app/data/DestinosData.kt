package com.example.aterrizar_app.data

import com.example.aterrizar_app.R
import com.example.aterrizar_app.model.Categoria
import com.example.aterrizar_app.model.Destino

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