package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.util.formatoPrecio
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EstadisticasViajesCard(
    reservas: List<Reserva>,
    modifier: Modifier = Modifier
) {
    val totalReservas = reservas.size
    val totalViajeros = reservas.sumOf { it.adultos + it.ninos }
    val totalInvertido = reservas.sumOf { it.total }
    val proximaSalida = remember(reservas) { obtenerProximaSalida(reservas) }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(colors = listOf(AzulOceano, Turquesa)))
                .padding(18.dp)
        ) {
            Text(
                text = "Resumen de tus viajes",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatoEstadistica(
                    etiqueta = "Reservas",
                    valor = totalReservas.toString(),
                    modifier = Modifier.weight(1f)
                )
                DatoEstadistica(
                    etiqueta = "Viajeros",
                    valor = totalViajeros.toString(),
                    modifier = Modifier.weight(1f)
                )
                DatoEstadistica(
                    etiqueta = "Total invertido",
                    valor = formatoPrecio(totalInvertido),
                    modifier = Modifier.weight(1.6f)
                )
            }
            if (proximaSalida != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.25f))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🗓️ Próxima salida",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "${proximaSalida.destino.nombre} · ${proximaSalida.fechaViaje}",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DatoEstadistica(
    etiqueta: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(end = 6.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.14f))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(
            text = etiqueta,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = valor,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1
        )
    }
}

private fun obtenerProximaSalida(reservas: List<Reserva>): Reserva? {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-CO")).apply {
        isLenient = false
    }
    val hoyInicio = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val conFechaValida = reservas.mapNotNull { reserva ->
        if (reserva.fechaViaje.isBlank()) return@mapNotNull null
        try {
            val millis = formato.parse(reserva.fechaViaje)?.time ?: return@mapNotNull null
            millis to reserva
        } catch (_: Exception) {
            null
        }
    }.sortedBy { it.first }

    val futuras = conFechaValida.filter { it.first >= hoyInicio }
    return (futuras.firstOrNull() ?: conFechaValida.firstOrNull())?.second
}