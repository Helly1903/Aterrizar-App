package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aterrizar_app.Categoria
import com.example.aterrizar_app.destinos
import com.example.aterrizar_app.ui.components.Buscador
import com.example.aterrizar_app.ui.components.CategoriasRow
import com.example.aterrizar_app.ui.components.DestinoCard
import com.example.aterrizar_app.ui.components.Header
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano

@Composable
fun InicioScreen() {
    var texto by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Header() }

        item { Buscador(texto = texto, onTextoChange = { texto = it }) }

        item {
            CategoriasRow(
                seleccionada = categoriaSeleccionada,
                onSeleccion = { categoriaSeleccionada = it }
            )
        }

        item {
            Text(
                text = "Destinos destacados",
                color = AzulOceano,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        items(destinos, key = { it.id }) { destino ->
            DestinoCard(destino = destino)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    AterrizarAppTheme {
        InicioScreen()
    }
}