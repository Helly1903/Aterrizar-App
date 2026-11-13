package com.example.aterrizar_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.components.CategoriaChip
import com.example.aterrizar_app.ui.components.ContadorViajeros
import com.example.aterrizar_app.ui.components.FilaResumen
import com.example.aterrizar_app.ui.components.SelectorFechaViaje
import com.example.aterrizar_app.ui.components.TituloSeccion
import com.example.aterrizar_app.ui.components.coloresCampo
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.NaranjaAtardecer
import com.example.aterrizar_app.ui.theme.Turquesa
import com.example.aterrizar_app.ui.theme.VerdeMontana
import com.example.aterrizar_app.util.calcularCotizacion
import com.example.aterrizar_app.util.formatoPrecio

private val correoEditarRegex = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")

@Composable
fun EditarReservaScreen(
    reserva: Reserva,
    onVolver: () -> Unit,
    onGuardarCambios: (String, String, Int, Int, Plan, Int, String, String) -> Unit
) {
    var nombre by remember(reserva.id) { mutableStateOf(reserva.nombre) }
    var correo by remember(reserva.id) { mutableStateOf(reserva.correo) }
    var fechaViaje by remember(reserva.id) { mutableStateOf(reserva.fechaViaje) }
    var adultos by remember(reserva.id) { mutableIntStateOf(reserva.adultos) }
    var ninos by remember(reserva.id) { mutableIntStateOf(reserva.ninos) }
    var plan by remember(reserva.id) { mutableStateOf(reserva.plan) }
    var notas by remember(reserva.id) { mutableStateOf(reserva.notas) }

    val nombreValido = nombre.trim().length >= 3
    val correoValido = correoEditarRegex.matches(correo.trim())
    val fechaValida = fechaViaje.isNotBlank()
    val formularioValido = nombreValido && correoValido && fechaValida

    val cotizacion = calcularCotizacion(reserva.destino, plan, adultos, ninos)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
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
                text = "Editar reserva #${reserva.id}",
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
            Text(
                text = "${reserva.destino.precioTexto} por persona · 📅 ${reserva.destino.duracion}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 14.sp
            )

            TituloSeccion("Datos del titular")
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                isError = nombre.isNotEmpty() && !nombreValido,
                supportingText = {
                    if (nombre.isNotEmpty() && !nombreValido) {
                        Text("Escribe al menos 3 letras")
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = coloresCampo(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = correo.isNotEmpty() && !correoValido,
                supportingText = {
                    if (correo.isNotEmpty() && !correoValido) {
                        Text("Escribe un correo válido, por ejemplo nombre@correo.com")
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = coloresCampo(),
                modifier = Modifier.fillMaxWidth()
            )

            TituloSeccion("Fecha del viaje")
            SelectorFechaViaje(
                fechaSeleccionada = fechaViaje,
                onFechaSeleccionada = { fechaViaje = it }
            )

            TituloSeccion("Viajeros")
            ContadorViajeros(
                etiqueta = "Adultos",
                detalle = "Precio completo",
                cantidad = adultos,
                minimo = 1,
                maximo = 10,
                onMenos = { adultos-- },
                onMas = { adultos++ }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ContadorViajeros(
                etiqueta = "Niños",
                detalle = "Pagan el 50%",
                cantidad = ninos,
                minimo = 0,
                maximo = 10,
                onMenos = { ninos-- },
                onMas = { ninos++ }
            )

            TituloSeccion("Plan")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (opcion in Plan.entries) {
                    CategoriaChip(
                        texto = opcion.etiqueta,
                        activa = plan == opcion,
                        onClick = { plan = opcion }
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Precio base × ${plan.multiplicador}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            TituloSeccion("Notas especiales (opcional)")
            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Ej. Equipaje extra, habitación doble, dieta") },
                maxLines = 3,
                shape = RoundedCornerShape(16.dp),
                colors = coloresCampo(),
                modifier = Modifier.fillMaxWidth()
            )

            TituloSeccion("Resumen actualizado")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    FilaResumen(
                        etiqueta = "Subtotal",
                        valor = formatoPrecio(cotizacion.subtotal)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (cotizacion.descuento > 0) {
                        FilaResumen(
                            etiqueta = "Descuento de grupo (10%)",
                            valor = "-" + formatoPrecio(cotizacion.descuento),
                            colorValor = VerdeMontana
                        )
                    } else {
                        Text(
                            text = "Con 4 o más viajeros obtienes 10% de descuento.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    FilaResumen(
                        etiqueta = "Nuevo total",
                        valor = formatoPrecio(cotizacion.total),
                        colorValor = NaranjaAtardecer,
                        destacado = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    onGuardarCambios(
                        nombre.trim(),
                        correo.trim(),
                        adultos,
                        ninos,
                        plan,
                        cotizacion.total,
                        fechaViaje,
                        notas.trim()
                    )
                },
                enabled = formularioValido,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulOceano,
                    disabledContainerColor = AzulOceano.copy(alpha = 0.4f),
                    disabledContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Guardar cambios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}