package com.example.aterrizar_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aterrizar_app.data.destinos
import com.example.aterrizar_app.model.ChecklistItem
import com.example.aterrizar_app.model.Destino
import com.example.aterrizar_app.model.Plan
import com.example.aterrizar_app.model.Reserva
import com.example.aterrizar_app.ui.screens.DetalleViajeScreen
import com.example.aterrizar_app.ui.screens.EditarReservaScreen
import com.example.aterrizar_app.ui.screens.FavoritosScreen
import com.example.aterrizar_app.ui.screens.InicioScreen
import com.example.aterrizar_app.ui.screens.MisViajesScreen
import com.example.aterrizar_app.ui.screens.ReservarScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun AterrizarNavHost(
    navController: NavHostController,
    favoritos: Set<Int>,
    reservas: List<Reserva>,
    observarChecklist: (Int) -> Flow<List<ChecklistItem>>,
    onAlternarFavorito: (Int) -> Unit,
    onCancelarReserva: (Int) -> Unit,
    onConfirmarReserva: (Destino, String, String, Int, Int, Plan, Int, String, String) -> Unit,
    onActualizarReserva: (Int, Int, String, String, Int, Int, Plan, Int, String, String) -> Unit,
    onAgregarChecklistItem: (Int, String) -> Unit,
    onAlternarChecklistItem: (ChecklistItem) -> Unit,
    onEliminarChecklistItem: (ChecklistItem) -> Unit,
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
                onVerDetalle = { id -> navController.navigate(Rutas.detalleViajeConId(id)) },
                onEditar = { id -> navController.navigate(Rutas.editarReservaConId(id)) },
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
                    onConfirmar = { nombre, correo, adultos, ninos, plan, total, fechaViaje, notas ->
                        onConfirmarReserva(
                            destino,
                            nombre,
                            correo,
                            adultos,
                            ninos,
                            plan,
                            total,
                            fechaViaje,
                            notas
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            route = Rutas.EDITAR_RESERVA,
            arguments = listOf(navArgument("reservaId") { type = NavType.IntType })
        ) { entrada ->
            val reservaId = entrada.arguments?.getInt("reservaId")
            val reserva = reservas.find { it.id == reservaId }
            if (reserva != null) {
                EditarReservaScreen(
                    reserva = reserva,
                    onVolver = { navController.popBackStack() },
                    onGuardarCambios = { nombre, correo, adultos, ninos, plan, total, fechaViaje, notas ->
                        onActualizarReserva(
                            reserva.id,
                            reserva.destino.id,
                            nombre,
                            correo,
                            adultos,
                            ninos,
                            plan,
                            total,
                            fechaViaje,
                            notas
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            route = Rutas.DETALLE_VIAJE,
            arguments = listOf(navArgument("reservaId") { type = NavType.IntType })
        ) { entrada ->
            val reservaId = entrada.arguments?.getInt("reservaId") ?: return@composable
            val reserva = reservas.find { it.id == reservaId }
            if (reserva != null) {
                val checklist by remember(reservaId) {
                    observarChecklist(reservaId)
                }.collectAsState(initial = emptyList())

                DetalleViajeScreen(
                    reserva = reserva,
                    checklist = checklist,
                    onVolver = { navController.popBackStack() },
                    onEditarReserva = {
                        navController.navigate(Rutas.editarReservaConId(reserva.id))
                    },
                    onAgregarItem = { titulo ->
                        onAgregarChecklistItem(reserva.id, titulo)
                    },
                    onAlternarItem = onAlternarChecklistItem,
                    onEliminarItem = onEliminarChecklistItem
                )
            }
        }
    }
}