package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.model.ChecklistItem
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.components.CategoriaChip
import com.example.aterrizar_app.ui.components.FilaResumen
import com.example.aterrizar_app.ui.components.TituloSeccion
import com.example.aterrizar_app.ui.components.coloresCampo
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.NaranjaAtardecer
import com.example.aterrizar_app.ui.theme.RojoCancelar
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.ui.theme.VerdeMontana
import com.example.aterrizar_app.util.formatoPrecio
import com.example.aterrizar_app.util.textoViajeros

private val sugerenciasChecklist = listOf(
    "Pasaporte / documento",
    "Check-in en línea",
    "Seguro médico",
    "Confirmar hotel",
    "Preparar equipaje"
)

@Composable
fun DetalleViajeScreen(
    reserva: Reserva,
    checklist: List<ChecklistItem>,
    onVolver: () -> Unit,
    onEditarReserva: () -> Unit,
    onAgregarItem: (String) -> Unit,
    onAlternarItem: (ChecklistItem) -> Unit,
    onEliminarItem: (ChecklistItem) -> Unit
) {
    var nuevoItem by remember { mutableStateOf("") }

    val completados = checklist.count { it.completado }
    val totalItems = checklist.size
    val progreso = if (totalItems > 0) completados.toFloat() / totalItems else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) {
            Image(
                painter = painterResource(id = reserva.destino.imagen),
                contentDescription = reserva.destino.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable { onVolver() }
            ) {
                Text(
                    text = "←",
                    color = AzulOceano,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Reserva #${reserva.id}",
                color = Turquesa,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${reserva.destino.nombre}, ${reserva.destino.pais}",
                color = AzulOceano,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📅 ${reserva.destino.duracion}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = reserva.destino.descripcion,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            TituloSeccion("Información de la reserva")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilaResumen(etiqueta = "Titular", valor = reserva.nombre)
                    FilaResumen(etiqueta = "Correo", valor = reserva.correo)
                    FilaResumen(
                        etiqueta = "Fecha de salida",
                        valor = reserva.fechaViaje.ifBlank { "Por definir" }
                    )
                    FilaResumen(
                        etiqueta = "Viajeros",
                        valor = textoViajeros(reserva.adultos, reserva.ninos)
                    )
                    FilaResumen(etiqueta = "Plan elegido", valor = reserva.plan.etiqueta)
                    if (reserva.notas.isNotBlank()) {
                        FilaResumen(etiqueta = "Notas", valor = reserva.notas)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    FilaResumen(
                        etiqueta = "Total",
                        valor = formatoPrecio(reserva.total),
                        colorValor = NaranjaAtardecer,
                        destacado = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = onEditarReserva,
                        border = BorderStroke(1.dp, AzulOceano),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AzulOceano),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar datos de la reserva")
                    }
                }
            }

            TituloSeccion("Checklist de viaje")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Preparación del viaje",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = AzulOceano
                        )
                        Text(
                            text = if (totalItems == 0) "Sin tareas" else "$completados de $totalItems",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (totalItems > 0 && completados == totalItems) VerdeMontana else Turquesa
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progreso },
                        color = VerdeMontana,
                        trackColor = VerdeMontana.copy(alpha = 0.15f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = nuevoItem,
                            onValueChange = { nuevoItem = it },
                            placeholder = { Text("Ej. Llevar pasaporte") },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = coloresCampo(),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (nuevoItem.isNotBlank()) {
                                    onAgregarItem(nuevoItem.trim())
                                    nuevoItem = ""
                                }
                            },
                            enabled = nuevoItem.isNotBlank(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Turquesa),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar tarea",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Sugerencias rápidas:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(sugerenciasChecklist) { sugerencia ->
                            val yaExiste = checklist.any {
                                it.titulo.equals(sugerencia, ignoreCase = true)
                            }
                            if (!yaExiste) {
                                CategoriaChip(
                                    texto = "+ $sugerencia",
                                    activa = false,
                                    onClick = { onAgregarItem(sugerencia) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    if (checklist.isEmpty()) {
                        Text(
                            text = "Aún no tienes pendientes para este viaje. Agrega una tarea o toca una sugerencia.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            checklist.forEach { item ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { onAlternarItem(item) }
                                        .padding(vertical = 2.dp)
                                ) {
                                    Checkbox(
                                        checked = item.completado,
                                        onCheckedChange = { onAlternarItem(item) },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = VerdeMontana,
                                            uncheckedColor = AzulOceano
                                        )
                                    )
                                    Text(
                                        text = item.titulo,
                                        fontSize = 14.sp,
                                        textDecoration = if (item.completado) {
                                            TextDecoration.LineThrough
                                        } else {
                                            TextDecoration.None
                                        },
                                        color = if (item.completado) {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(onClick = { onEliminarItem(item) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar tarea",
                                            tint = RojoCancelar.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}