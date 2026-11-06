package com.example.aterrizar_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva

class AterrizarViewModel : ViewModel() {

    // Estado de favoritos (conjunto de IDs de destinos guardados)
    var favoritos by mutableStateOf<Set<Int>>(emptySet())
        private set

    // Estado de reservas registradas en la aplicación
    var reservas by mutableStateOf<List<Reserva>>(emptyList())
        private set

    /**
     * Agrega o remueve un destino del conjunto de favoritos según su ID
     */
    fun alternarFavorito(destinoId: Int) {
        favoritos = if (destinoId in favoritos) {
            favoritos - destinoId
        } else {
            favoritos + destinoId
        }
    }

    /**
     * Registra una nueva reserva generando un ID único autoincremental
     */
    fun confirmarReserva(
        destino: Destino,
        nombre: String,
        correo: String,
        adultos: Int,
        ninos: Int,
        plan: Plan,
        total: Int
    ) {
        val nuevoId = (reservas.maxOfOrNull { it.id } ?: 0) + 1
        val nuevaReserva = Reserva(
            id = nuevoId,
            destino = destino,
            nombre = nombre,
            correo = correo,
            adultos = adultos,
            ninos = ninos,
            plan = plan,
            total = total
        )
        reservas = reservas + nuevaReserva
    }

    /**
     * Cancela y remueve una reserva existente mediante su ID
     */
    fun cancelarReserva(reservaId: Int) {
        reservas = reservas.filter { it.id != reservaId }
    }
}