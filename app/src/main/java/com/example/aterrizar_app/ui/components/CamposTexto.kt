package com.example.aterrizar_app.ui.components

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.aterrizar_app.ui.theme.BordeArena
import com.example.aterrizar_app.ui.theme.Turquesa

@Composable
fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = Turquesa,
    unfocusedBorderColor = BordeArena
)