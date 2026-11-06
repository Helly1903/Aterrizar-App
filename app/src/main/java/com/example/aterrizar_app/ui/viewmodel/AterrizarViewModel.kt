package com.example.aterrizar_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aterrizar_app.data.repository.AterrizarRepository
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
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
        total: Int
    ) {
        viewModelScope.launch {
            repository.confirmarReserva(destino, nombre, correo, adultos, ninos, plan, total)
        }
    }

    fun cancelarReserva(reservaId: Int) {
        viewModelScope.launch {
            repository.cancelarReserva(reservaId)
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