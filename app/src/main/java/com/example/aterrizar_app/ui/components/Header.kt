package com.example.aterrizar_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.R
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa

val FuenteLogo = FontFamily(Font(R.font.fredoka_bold, FontWeight.Bold))

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Brush.linearGradient(colors = listOf(AzulOceano, Turquesa)))
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .offset(x = 50.dp, y = (-40).dp)
                .background(Color.White.copy(alpha = 0.10f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .background(Color.White.copy(alpha = 0.08f), CircleShape)
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = "Aterrizar CO",
                color = Color.White,
                fontFamily = FuenteLogo,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                letterSpacing = 1.sp
            )

            EstelaDeVuelo()

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Text(
                    text = "Tu próximo destino te espera",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun EstelaDeVuelo() {
    Canvas(
        modifier = Modifier
            .width(220.dp)
            .height(36.dp)
    ) {
        val finX = size.width * 0.9f
        val finY = size.height * 0.2f

        val trayectoria = Path().apply {
            moveTo(0f, size.height * 0.85f)
            quadraticBezierTo(
                size.width * 0.5f, size.height * -0.3f,
                finX, finY
            )
        }
        drawPath(
            path = trayectoria,
            color = Color.White,
            style = Stroke(
                width = 6f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 16f))
            )
        )

        val avion = Path().apply {
            moveTo(finX + 44f, finY)
            lineTo(finX - 24f, finY - 26f)
            lineTo(finX - 10f, finY)
            lineTo(finX - 24f, finY + 26f)
            close()
        }
        rotate(degrees = -20f, pivot = Offset(finX, finY)) {
            drawPath(path = avion, color = Color.White)
        }
    }
}