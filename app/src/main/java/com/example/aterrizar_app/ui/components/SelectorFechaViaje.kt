package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFechaViaje(
    fechaSeleccionada: String,
    onFechaSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var mostrarCalendario by remember { mutableStateOf(false) }

    val hoyUtcMillis = remember { obtenerInicioHoyUtcMillis() }
    val anioActual = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val millisIniciales = remember(fechaSeleccionada) {
        parsearFechaUtcMillis(fechaSeleccionada)
    }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = fechaSeleccionada,
            onValueChange = {},
            readOnly = true,
            label = { Text("Fecha de salida") },
            placeholder = { Text("Selecciona día, mes y año") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Abrir calendario",
                    tint = Turquesa
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = coloresCampo(),
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { mostrarCalendario = true }
        )
    }

    if (mostrarCalendario) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = millisIniciales,
            yearRange = anioActual..(anioActual + 5),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= hoyUtcMillis
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return year >= anioActual
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onFechaSeleccionada(formatearFechaUtc(millis))
                        }
                        mostrarCalendario = false
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("Aceptar", color = AzulOceano)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "Fecha del viaje",
                        modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
                    )
                },
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = AzulOceano,
                    todayDateBorderColor = Turquesa,
                    todayContentColor = AzulOceano,
                    selectedYearContainerColor = AzulOceano
                )
            )
        }
    }
}

private fun obtenerInicioHoyUtcMillis(): Long {
    val local = Calendar.getInstance()
    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(
            local.get(Calendar.YEAR),
            local.get(Calendar.MONTH),
            local.get(Calendar.DAY_OF_MONTH),
            0,
            0,
            0
        )
        set(Calendar.MILLISECOND, 0)
    }
    return utc.timeInMillis
}

private fun formatearFechaUtc(utcMillis: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-CO")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return formato.format(Date(utcMillis))
}

private fun parsearFechaUtcMillis(fechaTexto: String): Long? {
    if (fechaTexto.isBlank()) return null
    return try {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-CO")).apply {
            timeZone = TimeZone.getTimeZone("UTC")
            isLenient = false
        }
        formato.parse(fechaTexto)?.time
    } catch (_: Exception) {
        null
    }
}