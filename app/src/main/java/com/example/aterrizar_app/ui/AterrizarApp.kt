package com.example.aterrizar_app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aterrizar_app.ui.navigation.AterrizarNavHost
import com.example.aterrizar_app.ui.navigation.BarraInferior
import com.example.aterrizar_app.ui.navigation.Rutas
import com.example.aterrizar_app.ui.viewmodel.AterrizarViewModel

@Composable
fun AterrizarApp(
    viewModel: AterrizarViewModel
) {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    val favoritos by viewModel.favoritos.collectAsState()
    val reservas by viewModel.reservas.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
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
            onAlternarFavorito = { id -> viewModel.alternarFavorito(id) },
            onCancelarReserva = { id -> viewModel.cancelarReserva(id) },
            onConfirmarReserva = { destino, nombre, correo, adultos, ninos, plan, total ->
                viewModel.confirmarReserva(
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