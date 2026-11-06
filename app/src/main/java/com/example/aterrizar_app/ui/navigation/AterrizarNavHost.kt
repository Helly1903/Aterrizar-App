package com.example.aterrizar_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.screens.FavoritosScreen
import com.example.aterrizar_app.ui.screens.InicioScreen
import com.example.aterrizar_app.ui.screens.MisViajesScreen
import com.example.aterrizar_app.ui.screens.ReservarScreen

@Composable
fun AterrizarNavHost(
    navController: NavHostController,
    favoritos: Set<Int>,
    reservas: List<Reserva>,
    onAlternarFavorito: (Int) -> Unit,
    onCancelarReserva: (Int) -> Unit,
    onConfirmarReserva: (Destino, String, String, Int, Int, Plan, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.INICIO,
        modifier = modifier
    ) {
        composable(Rutas.INICIO) {
            InicioScreen(
                favoritos = favoritos,
                onAlternarFavorito = onAlternarFavorito,
                onReservar = { id -> navController.navigate(Rutas.reservarConId(id)) }
            )
        }
        composable(Rutas.FAVORITOS) {
            FavoritosScreen(
                favoritos = favoritos,
                onAlternarFavorito = onAlternarFavorito,
                onReservar = { id -> navController.navigate(Rutas.reservarConId(id)) }
            )
        }
        composable(Rutas.MIS_VIAJES) {
            MisViajesScreen(
                reservas = reservas,
                onCancelar = onCancelarReserva
            )
        }
        composable(
            route = Rutas.RESERVAR,
            arguments = listOf(navArgument("destinoId") { type = NavType.IntType })
        ) { entrada ->
            val destinoId = entrada.arguments?.getInt("destinoId")
            val destino = destinos.find { it.id == destinoId }
            if (destino != null) {
                ReservarScreen(
                    destino = destino,
                    onVolver = { navController.popBackStack() },
                    onConfirmar = { nombre, correo, adultos, ninos, plan, total ->
                        onConfirmarReserva(destino, nombre, correo, adultos, ninos, plan, total)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}