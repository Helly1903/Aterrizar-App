package com.example.aterrizar_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = AzulOceano,
    secondary = Turquesa,
    tertiary = NaranjaAtardecer,
    background = Arena,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextoOscuro,
    onSurface = TextoOscuro
)

@Composable
fun AterrizarAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}