package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.Plan
import com.example.aterrizar_app.Reserva
import com.example.aterrizar_app.destinos
import com.example.aterrizar_app.formatoPrecio
import com.example.aterrizar_app.ui.components.EstadoVacio
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.NaranjaAtardecer
import com.example.aterrizar_app.ui.theme.Turquesa

private val RojoCancelar = Color(0xFFC62828)

@Composable
fun MisViajesScreen(
    reservas: List<Reserva>,
    onCancelar: (Int) -> Unit
) {
    // Reserva que el usuario quiere cancelar (null = ningún diálogo abierto)
    var reservaPorCancelar by remember { mutableStateOf<Reserva?>(null) }

    if (reservas.isEmpty()) {
        EstadoVacio(
            emoji = "🧳",
            titulo = "Aún no tienes viajes",
            mensaje = "Reserva un destino y aparecerá aquí."
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Mis viajes (${reservas.size})",
                        color = AzulOceano,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Total reservado: ${formatoPrecio(reservas.sumOf { it.total })}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            items(reservas, key = { it.id }) { reserva ->
                ReservaCard(
                    reserva = reserva,
                    onCancelarClick = { reservaPorCancelar = reserva }
                )
            }
        }
    }

    // Diálogo de confirmación
    reservaPorCancelar?.let { reserva ->
        AlertDialog(
            onDismissRequest = { reservaPorCancelar = null },
            title = { Text("¿Cancelar reserva?") },
            text = { Text("Se cancelará tu viaje a ${reserva.destino.nombre}.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCancelar(reserva.id)
                        reservaPorCancelar = null
                    }
                ) {
                    Text("Sí, cancelar", color = RojoCancelar)
                }
            },
            dismissButton = {
                TextButton(onClick = { reservaPorCancelar = null }) {
                    Text("No, mantener")
                }
            }
        )
    }
}

@Composable
fun ReservaCard(
    reserva: Reserva,
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
                        text = "🕒 ${reserva.destino.duracion}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Titular: ${reserva.nombre}",
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatoPrecio(reserva.total),
                color = NaranjaAtardecer,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onCancelarClick,
                border = BorderStroke(1.dp, RojoCancelar),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RojoCancelar),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar reserva")
            }
        }
    }
}

// "1 adulto" / "2 adultos · 1 niño"
private fun textoViajeros(adultos: Int, ninos: Int): String {
    val textoAdultos = if (adultos == 1) "1 adulto" else "$adultos adultos"
    if (ninos == 0) return textoAdultos
    val textoNinos = if (ninos == 1) "1 niño" else "$ninos niños"
    return "$textoAdultos · $textoNinos"
}

@Preview(showBackground = true)
@Composable
fun MisViajesScreenPreview() {
    AterrizarAppTheme {
        MisViajesScreen(
            reservas = listOf(
                Reserva(
                    id = 1,
                    destino = destinos.first(),
                    nombre = "Ana Gómez",
                    correo = "ana@correo.com",
                    adultos = 2,
                    ninos = 1,
                    plan = Plan.CONFORT,
                    total = 5781250
                )
            ),
            onCancelar = {}
        )
    }
}