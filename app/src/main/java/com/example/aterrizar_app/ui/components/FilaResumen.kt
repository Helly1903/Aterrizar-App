package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FilaResumen(
    etiqueta: String,
    valor: String,
    colorValor: Color = Color.Unspecified,
    destacado: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            fontSize = if (destacado) 18.sp else 14.sp,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = valor,
            color = if (colorValor == Color.Unspecified) {
                MaterialTheme.colorScheme.onSurface
            } else {
                colorValor
            },
            fontSize = if (destacado) 22.sp else 14.sp,
            fontWeight = if (destacado) FontWeight.ExtraBold else FontWeight.SemiBold
        )
    }
}