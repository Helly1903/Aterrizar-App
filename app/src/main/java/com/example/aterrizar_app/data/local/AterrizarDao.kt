package com.example.aterrizar_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AterrizarDao {

    @Query("SELECT * FROM favoritos")
    fun observeFavoritos(): Flow<List<FavoritoEntity>>

    @Query("SELECT * FROM favoritos WHERE destinoId = :destinoId LIMIT 1")
    suspend fun findFavoritoById(destinoId: Int): FavoritoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorito(favorito: FavoritoEntity)

    @Delete
    suspend fun deleteFavorito(favorito: FavoritoEntity)

    @Query("SELECT * FROM reservas ORDER BY id DESC")
    fun observeReservas(): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE id = :id LIMIT 1")
    suspend fun findReservaById(id: Int): ReservaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reserva: ReservaEntity)

    @Delete
    suspend fun deleteReserva(reserva: ReservaEntity)
}