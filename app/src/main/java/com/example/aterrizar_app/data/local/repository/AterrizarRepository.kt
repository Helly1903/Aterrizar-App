package com.example.aterrizar_app.data.repository

import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.data.local.AterrizarDao
import com.example.aterrizar_app.data.local.FavoritoEntity
import com.example.aterrizar_app.data.local.ReservaEntity
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AterrizarRepository(
    private val dao: AterrizarDao
) {

    val favoritos: Flow<Set<Int>> = dao.observeFavoritos().map { entities ->
        entities.map { it.destinoId }.toSet()
    }

    val reservas: Flow<List<Reserva>> = dao.observeReservas().map { entities ->
        entities.mapNotNull { entity -> entity.toModel() }
    }

    suspend fun alternarFavorito(destinoId: Int) {
        val existente = dao.findFavoritoById(destinoId)
        if (existente != null) {
            dao.deleteFavorito(existente)
        } else {
            dao.insertFavorito(FavoritoEntity(destinoId = destinoId))
        }
    }

    suspend fun confirmarReserva(
        destino: Destino,
        nombre: String,
        correo: String,
        adultos: Int,
        ninos: Int,
        plan: Plan,
        total: Int
    ) {
        val entity = ReservaEntity(
            destinoId = destino.id,
            nombre = nombre,
            correo = correo,
            adultos = adultos,
            ninos = ninos,
            plan = plan.name,
            total = total
        )
        dao.insertReserva(entity)
    }

    suspend fun cancelarReserva(reservaId: Int) {
        val existente = dao.findReservaById(reservaId) ?: return
        dao.deleteReserva(existente)
    }
}

private fun ReservaEntity.toModel(): Reserva? {
    val destinoEncontrado = destinos.find { it.id == destinoId } ?: return null
    val planEncontrado = Plan.entries.firstOrNull { it.name == plan } ?: Plan.BASICO
    return Reserva(
        id = id,
        destino = destinoEncontrado,
        nombre = nombre,
        correo = correo,
        adultos = adultos,
        ninos = ninos,
        plan = planEncontrado,
        total = total
    )
}