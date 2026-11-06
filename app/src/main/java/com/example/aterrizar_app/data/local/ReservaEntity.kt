package com.example.aterrizar_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class ReservaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val destinoId: Int,
    val nombre: String,
    val correo: String,
    val adultos: Int,
    val ninos: Int,
    val plan: String,
    val total: Int
)