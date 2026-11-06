package com.example.aterrizar_app.model

// Plan de viaje: el multiplicador cambia el precio base
enum class Plan(val etiqueta: String, val multiplicador: Double) {
    BASICO("Básico", 1.0),
    CONFORT("Confort", 1.25),
    PREMIUM("Premium", 1.5)
}