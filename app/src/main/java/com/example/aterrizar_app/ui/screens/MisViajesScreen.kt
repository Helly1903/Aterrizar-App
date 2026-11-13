package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.components.CategoriaChip
import com.example.aterrizar_app.ui.components.EstadisticasViajesCard
import com.example.aterrizar_app.ui.components.EstadoVacio
import com.example.aterrizar_app.ui.components.ReservaCard
import com.example.aterrizar_app.ui.components.coloresCampo
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.RojoCancelar
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.util.sinTildes

@Composable
fun MisViajesScreen(
    reservas: List<Reserva>,
    onVerDetalle: (Int) -> Unit = {},
    onEditar: (Int) -> Unit = {},
    onCancelar: (Int) -> Unit
) {
    var reservaPorCancelar by remember { mutableStateOf<Reserva?>(null) }
    var textoBusqueda by remember { mutableStateOf("") }
    var planSeleccionado by remember { mutableStateOf<Plan?>(null) }

    if (reservas.isEmpty()) {
        EstadoVacio(
            emoji = "✈️",
            titulo = "Aún no tienes viajes",
            mensaje = "Reserva un destino y aparecerá aquí."
        )
    } else {
        val consulta = textoBusqueda.trim().sinTildes()
        val reservasFiltradas = reservas.filter { reserva ->
            val coincideTexto = consulta.isEmpty() ||
                    reserva.destino.nombre.sinTildes().contains(consulta, ignoreCase = true) ||
                    reserva.destino.pais.sinTildes().contains(consulta, ignoreCase = true) ||
                    reserva.nombre.sinTildes().contains(consulta, ignoreCase = true) ||
                    reserva.fechaViaje.contains(consulta, ignoreCase = true)

            val coincidePlan = planSeleccionado == null || reserva.plan == planSeleccionado
            coincideTexto && coincidePlan
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Mis viajes (${reservas.size})",
                    color = AzulOceano,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp)
                )
            }
            item {
                EstadisticasViajesCard(reservas = reservas)
            }
            item {
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = { textoBusqueda = it },
                    placeholder = { Text("Buscar por destino, titular o fecha...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar reserva",
                            tint = Turquesa
                        )
                    },
                    trailingIcon = {
                        if (textoBusqueda.isNotEmpty()) {
                            IconButton(onClick = { textoBusqueda = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar búsqueda"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = coloresCampo(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        CategoriaChip(
                            texto = "Todos",
                            activa = planSeleccionado == null,
                            onClick = { planSeleccionado = null }
                        )
                    }
                    items(Plan.entries) { plan ->
                        CategoriaChip(
                            texto = "Plan ${plan.etiqueta}",
                            activa = planSeleccionado == plan,
                            onClick = { planSeleccionado = plan }
                        )
                    }
                }
            }

            if (reservasFiltradas.isEmpty()) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                    ) {
                        Text(
                            text = "No hay reservas que coincidan con el filtro aplicado.",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = {
                                textoBusqueda = ""
                                planSeleccionado = null
                            },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Limpiar filtros", color = AzulOceano)
                        }
                    }
                }
            } else {
                items(reservasFiltradas, key = { it.id }) { reserva ->
                    ReservaCard(
                        reserva = reserva,
                        onVerDetalleClick = { onVerDetalle(reserva.id) },
                        onEditarClick = { onEditar(reserva.id) },
                        onCancelarClick = { reservaPorCancelar = reserva }
                    )
                }
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
                    total = 5781250,
                    fechaViaje = "15/12/2026",
                    notas = "Equipaje de bodega incluido"
                )
            ),
            onVerDetalle = {},
            onEditar = {},
            onCancelar = {}
        )
    }
}