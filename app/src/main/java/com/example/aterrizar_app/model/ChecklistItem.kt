package com.example.aterrizar_app.model

data class ChecklistItem(
    val id: Int,
    val reservaId: Int,
    val titulo: String,
    val completado: Boolean
)