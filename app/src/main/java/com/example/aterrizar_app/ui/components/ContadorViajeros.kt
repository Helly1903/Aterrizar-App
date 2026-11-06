package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.GrisDeshabilitado

@Composable
fun ContadorViajeros(
    etiqueta: String,
    detalle: String,
    cantidad: Int,
    minimo: Int,
    maximo: Int,
    onMenos: () -> Unit,
    onMas: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = etiqueta, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = detalle,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BotonRedondo(simbolo = "−", habilitado = cantidad > minimo, onClick = onMenos)
            Text(text = cantidad.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            BotonRedondo(simbolo = "+", habilitado = cantidad < maximo, onClick = onMas)
        }
    }
}

@Composable
private fun BotonRedondo(simbolo: String, habilitado: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (habilitado) AzulOceano else GrisDeshabilitado)
            .clickable(enabled = habilitado) { onClick() }
    ) {
        Text(
            text = simbolo,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}