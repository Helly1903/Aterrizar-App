package com.example.aterrizar_app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.navigation.AterrizarNavHost
import com.example.aterrizar_app.ui.navigation.BarraInferior
import com.example.aterrizar_app.ui.navigation.Rutas

@Composable
fun AterrizarApp() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // Estado compartido (en la etapa 2 pasa al ViewModel)
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var reservas by remember { mutableStateOf<List<Reserva>>(emptyList()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            // La barra inferior se oculta en la pantalla de reserva
            if (rutaActual != Rutas.RESERVAR) {
                BarraInferior(
                    rutaActual = rutaActual,
                    cantidadFavoritos = favoritos.size,
                    cantidadReservas = reservas.size,
                    onNavegar = { ruta ->
                        navController.navigate(ruta) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        AterrizarNavHost(
            navController = navController,
            favoritos = favoritos,
            reservas = reservas,
            onAlternarFavorito = { id ->
                favoritos = if (id in favoritos) favoritos - id else favoritos + id
            },
            onCancelarReserva = { id ->
                reservas = reservas.filter { it.id != id }
            },
            onConfirmarReserva = { destino, nombre, correo, adultos, ninos, plan, total ->
                val nuevoId = (reservas.maxOfOrNull { it.id } ?: 0) + 1
                reservas = reservas + Reserva(
                    id = nuevoId,
                    destino = destino,
                    nombre = nombre,
                    correo = correo,
                    adultos = adultos,
                    ninos = ninos,
                    plan = plan,
                    total = total
                )
            },
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}