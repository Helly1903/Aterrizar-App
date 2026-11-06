package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.components.EstadoVacio
import com.example.aterrizar_app.ui.components.ReservaCard
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.RojoCancelar
import com.example.aterrizar_app.util.formatoPrecio

@Composable
fun MisViajesScreen(
    reservas: List<Reserva>,
    onCancelar: (Int) -> Unit
) {
    var reservaPorCancelar by remember { mutableStateOf<Reserva?>(null) }

    if (reservas.isEmpty()) {
        EstadoVacio(
            emoji = "✈️",
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