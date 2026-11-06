package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.model.Categoria
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.BordeArena

@Composable
fun CategoriaChip(texto: String, activa: Boolean, onClick: () -> Unit) {
    Text(
        text = texto,
        color = if (activa) Color.White else AzulOceano,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (activa) AzulOceano else Color.White)
            .border(
                width = 1.dp,
                color = if (activa) AzulOceano else BordeArena,
                shape = RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun CategoriasRow(seleccionada: Categoria?, onSeleccion: (Categoria?) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            CategoriaChip(
                texto = "Todos",
                activa = seleccionada == null,
                onClick = { onSeleccion(null) }
            )
        }
        items(Categoria.entries) { categoria ->
            CategoriaChip(
                texto = categoria.etiqueta,
                activa = seleccionada == categoria,
                onClick = { onSeleccion(categoria) }
            )
        }
    }
}