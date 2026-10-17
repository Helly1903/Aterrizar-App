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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aterrizar_app.ui.navigation.Rutas
import com.example.aterrizar_app.ui.navigation.itemsBarra
import com.example.aterrizar_app.ui.screens.FavoritosScreen
import com.example.aterrizar_app.ui.screens.InicioScreen
import com.example.aterrizar_app.ui.screens.MisViajesScreen
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
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
                        label = { Text(text = item.etiqueta) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = AzulOceano,
                            indicatorColor = Turquesa.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.INICIO,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Rutas.INICIO) { InicioScreen() }
            composable(Rutas.FAVORITOS) { FavoritosScreen() }
            composable(Rutas.MIS_VIAJES) { MisViajesScreen() }
        }
    }
}