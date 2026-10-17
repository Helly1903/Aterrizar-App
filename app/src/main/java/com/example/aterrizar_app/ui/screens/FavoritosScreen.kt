package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aterrizar_app.destinos
import com.example.aterrizar_app.ui.components.DestinoCard
import com.example.aterrizar_app.ui.components.EstadoVacio
import com.example.aterrizar_app.ui.theme.AzulOceano

@Composable
fun FavoritosScreen(
    favoritos: Set<Int>,
    onAlternarFavorito: (Int) -> Unit,
    onReservar: (Int) -> Unit
) {
    val destinosFavoritos = destinos.filter { it.id in favoritos }

    if (destinosFavoritos.isEmpty()) {
        EstadoVacio(
            emoji = "🤍",
            titulo = "Aún no tienes favoritos",
            mensaje = "Toca el corazón en un destino para guardarlo aquí."
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tus favoritos (${destinosFavoritos.size})",
                    color = AzulOceano,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp)
                )
            }
            items(destinosFavoritos, key = { it.id }) { destino ->
                DestinoCard(
                    destino = destino,
                    esFavorito = true,
                    onFavoritoClick = { onAlternarFavorito(destino.id) },
                    onReservarClick = { onReservar(destino.id) }
                )
            }
        }
    }
}