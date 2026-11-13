package com.example.aterrizar_app.data.repository

import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.data.local.AterrizarDao
import com.example.aterrizar_app.data.local.ChecklistItemEntity
import com.example.aterrizar_app.data.local.FavoritoEntity
import com.example.aterrizar_app.data.local.ReservaEntity
import com.example.aterrizar_app.model.ChecklistItem
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
        total: Int,
        fechaViaje: String = "",
        notas: String = ""
    ) {
        val entity = ReservaEntity(
            destinoId = destino.id,
            nombre = nombre,
            correo = correo,
            adultos = adultos,
            ninos = ninos,
            plan = plan.name,
            total = total,
            fechaViaje = fechaViaje,
            notas = notas
        )
        dao.insertReserva(entity)
    }

    suspend fun actualizarReserva(
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
        val entity = ReservaEntity(
            id = reservaId,
            destinoId = destinoId,
            nombre = nombre,
            correo = correo,
            adultos = adultos,
            ninos = ninos,
            plan = plan.name,
            total = total,
            fechaViaje = fechaViaje,
            notas = notas
        )
        dao.updateReserva(entity)
    }

    suspend fun cancelarReserva(reservaId: Int) {
        val existente = dao.findReservaById(reservaId) ?: return
        dao.deleteReserva(existente)
    }

    fun observarChecklist(reservaId: Int): Flow<List<ChecklistItem>> =
        dao.observeChecklistPorReserva(reservaId).map { lista ->
            lista.map { it.toModel() }
        }

    suspend fun agregarChecklistItem(reservaId: Int, titulo: String) {
        dao.insertChecklistItem(
            ChecklistItemEntity(
                reservaId = reservaId,
                titulo = titulo.trim(),
                completado = false
            )
        )
    }

    suspend fun alternarChecklistItem(item: ChecklistItem) {
        dao.updateChecklistItem(
            ChecklistItemEntity(
                id = item.id,
                reservaId = item.reservaId,
                titulo = item.titulo,
                completado = !item.completado
            )
        )
    }

    suspend fun eliminarChecklistItem(item: ChecklistItem) {
        dao.deleteChecklistItem(
            ChecklistItemEntity(
                id = item.id,
                reservaId = item.reservaId,
                titulo = item.titulo,
                completado = item.completado
            )
        )
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
        total = total,
        fechaViaje = fechaViaje,
        notas = notas
    )
}

private fun ChecklistItemEntity.toModel(): ChecklistItem = ChecklistItem(
    id = id,
    reservaId = reservaId,
    titulo = titulo,
    completado = completado
)