package com.example.aterrizar_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aterrizar_app.ui.navigation.Rutas
import com.example.aterrizar_app.ui.navigation.itemsBarra
import com.example.aterrizar_app.ui.screens.FavoritosScreen
import com.example.aterrizar_app.ui.screens.InicioScreen
import com.example.aterrizar_app.ui.screens.MisViajesScreen
import com.example.aterrizar_app.ui.screens.ReservarScreen
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AterrizarAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AterrizarApp()
                }
            }
        }
    }
}

@Composable
fun AterrizarApp() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // Estado compartido: ids de los destinos marcados como favoritos
    var favoritos by remember { mutableStateOf<Set<Int>>(emptySet()) }

    // Estado compartido: reservas confirmadas
    var reservas by remember { mutableStateOf<List<Reserva>>(emptyList()) }

    fun alternarFavorito(id: Int) {
        favoritos = if (id in favoritos) favoritos - id else favoritos + id
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            // La barra inferior se oculta en la pantalla de reserva
            if (rutaActual != Rutas.RESERVAR) {
                NavigationBar(containerColor = Color.White) {
                    for (item in itemsBarra) {
                        NavigationBarItem(
                            selected = rutaActual == item.ruta,
                            onClick = {
                                navController.navigate(item.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(text = item.emoji, fontSize = 22.sp) },
                            label = {
                                val cantidad = when (item.ruta) {
                                    Rutas.FAVORITOS -> favoritos.size
                                    Rutas.MIS_VIAJES -> reservas.size
                                    else -> 0
                                }
                                val contador = if (cantidad > 0) " ($cantidad)" else ""
                                Text(text = item.etiqueta + contador)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = AzulOceano,
                                indicatorColor = Turquesa.copy(alpha = 0.2f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Rutas.INICIO) {
                InicioScreen(
                    favoritos = favoritos,
                    onAlternarFavorito = { alternarFavorito(it) },
                    onReservar = { id -> navController.navigate(Rutas.reservarConId(id)) }
                )
            }
            composable(Rutas.FAVORITOS) {
                FavoritosScreen(
                    favoritos = favoritos,
                    onAlternarFavorito = { alternarFavorito(it) },
                    onReservar = { id -> navController.navigate(Rutas.reservarConId(id)) }
                )
            }
            composable(Rutas.MIS_VIAJES) {
                MisViajesScreen()
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
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}