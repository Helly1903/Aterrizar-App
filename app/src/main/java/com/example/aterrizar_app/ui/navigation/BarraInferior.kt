package com.example.aterrizar_app.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa

@Composable
fun BarraInferior(
    rutaActual: String?,
    cantidadFavoritos: Int,
    cantidadReservas: Int,
    onNavegar: (String) -> Unit
) {
    NavigationBar(containerColor = Color.White) {
        for (item in itemsBarra) {
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = { onNavegar(item.ruta) },
                icon = { Text(text = item.emoji, fontSize = 22.sp) },
                label = {
                    val cantidad = when (item.ruta) {
                        Rutas.FAVORITOS -> cantidadFavoritos
                        Rutas.MIS_VIAJES -> cantidadReservas
                        else -> 0
                    }
                    val contador = if (cantidad > 0) " ($cantidad)" else ""
                    Text(text = item.etiqueta + contador)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = AzulOceano,
                    indicatorColor = Turquesa.copy(alpha = 0.2f)
                )
            )
        }
    }
}