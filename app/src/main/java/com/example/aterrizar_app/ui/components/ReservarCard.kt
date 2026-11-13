package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.NaranjaAtardecer
import com.example.aterrizar_app.ui.theme.RojoCancelar
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.util.formatoPrecio
import com.example.aterrizar_app.util.textoViajeros

@Composable
fun ReservaCard(
    reserva: Reserva,
    onVerDetalleClick: () -> Unit = {},
    onEditarClick: () -> Unit = {},
    onCancelarClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = reserva.destino.imagen),
                    contentDescription = reserva.destino.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reserva.destino.nombre,
                        color = AzulOceano,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = reserva.destino.pais,
                        color = Turquesa,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "📅 ${reserva.destino.duracion}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            if (reserva.fechaViaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "🗓️ Fecha de salida: ${reserva.fechaViaje}",
                    color = AzulOceano,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "👤 Titular: ${reserva.nombre}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = reserva.correo,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "👥 ${textoViajeros(reserva.adultos, reserva.ninos)} · Plan ${reserva.plan.etiqueta}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            if (reserva.notas.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "📝 ${reserva.notas}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatoPrecio(reserva.total),
                color = NaranjaAtardecer,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onVerDetalleClick,
                colors = ButtonDefaults.buttonColors(containerColor = Turquesa),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📋 Checklist y detalle")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onEditarClick,
                    border = BorderStroke(1.dp, AzulOceano),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AzulOceano),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }
                OutlinedButton(
                    onClick = onCancelarClick,
                    border = BorderStroke(1.dp, RojoCancelar),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RojoCancelar),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}