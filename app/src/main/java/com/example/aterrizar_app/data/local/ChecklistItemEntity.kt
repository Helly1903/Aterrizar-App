package com.example.aterrizar_app.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_items",
    foreignKeys = [
        ForeignKey(
            entity = ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["reservaId"])]
)
data class ChecklistItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reservaId: Int,
    val titulo: String,
    val completado: Boolean = false
)