package com.example.aterrizar_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aterrizar_app.data.repository.AterrizarRepository
import com.example.aterrizar_app.model.ChecklistItem
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AterrizarViewModel(
    private val repository: AterrizarRepository
) : ViewModel() {

    val favoritos = repository.favoritos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptySet()
    )

    val reservas = repository.reservas.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun alternarFavorito(destinoId: Int) {
        viewModelScope.launch {
            repository.alternarFavorito(destinoId)
        }
    }

    fun confirmarReserva(
        destino: Destino,
        nombre: String,
        correo: String,
        adultos: Int,
        ninos: Int,
        plan: Plan,
        total: Int,
        fechaViaje: String = "",
        notas: String = ""
    ) {
        viewModelScope.launch {
            repository.confirmarReserva(
                destino = destino,
                nombre = nombre,
                correo = correo,
                adultos = adultos,
                ninos = ninos,
                plan = plan,
                total = total,
                fechaViaje = fechaViaje,
                notas = notas
            )
        }
    }

    fun actualizarReserva(
        reservaId: Int,
        destinoId: Int,
        nombre: String,
        correo: String,
        adultos: Int,
        ninos: Int,
        plan: Plan,
        total: Int,
        fechaViaje: String,
        notas: String
    ) {
        viewModelScope.launch {
            repository.actualizarReserva(
                reservaId = reservaId,
                destinoId = destinoId,
                nombre = nombre,
                correo = correo,
                adultos = adultos,
                ninos = ninos,
                plan = plan,
                total = total,
                fechaViaje = fechaViaje,
                notas = notas
            )
        }
    }

    fun cancelarReserva(reservaId: Int) {
        viewModelScope.launch {
            repository.cancelarReserva(reservaId)
        }
    }

    fun observarChecklist(reservaId: Int): Flow<List<ChecklistItem>> =
        repository.observarChecklist(reservaId)

    fun agregarChecklistItem(reservaId: Int, titulo: String) {
        if (titulo.isBlank()) return
        viewModelScope.launch {
            repository.agregarChecklistItem(reservaId, titulo)
        }
    }

    fun alternarChecklistItem(item: ChecklistItem) {
        viewModelScope.launch {
            repository.alternarChecklistItem(item)
        }
    }

    fun eliminarChecklistItem(item: ChecklistItem) {
        viewModelScope.launch {
            repository.eliminarChecklistItem(item)
        }
    }
}

class AterrizarViewModelFactory(
    private val repository: AterrizarRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(AterrizarViewModel::class.java))
        return AterrizarViewModel(repository) as T
    }
}