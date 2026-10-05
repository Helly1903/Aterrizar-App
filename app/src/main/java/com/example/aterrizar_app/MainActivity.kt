package com.example.aterrizar_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.theme.AzulOceano
import com.example.aterrizar_app.ui.theme.Turquesa
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.aterrizar_app.ui.theme.NaranjaAtardecer
import com.example.aterrizar_app.ui.theme.OcreHistoria

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Header()

        Spacer(modifier = Modifier.height(20.dp))

        Buscador()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Destinos destacados",
            color = AzulOceano,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        DestinoCard(
            nombre = "Cartagena",
            pais = "Colombia",
            descripcion = "Murallas históricas, calles coloridas y playas del Caribe.",
            duracion = "5 días / 4 noches",
            precio = "$1.850.000",
            categoria = "🏛️ Historia",
            colorCategoria = OcreHistoria,
            imagen = R.drawable.cartagena
        )

        Spacer(modifier = Modifier.height(16.dp))

        DestinoCard(
            nombre = "Cancún",
            pais = "México",
            descripcion = "Arena blanca, mar turquesa y vida nocturna inolvidable.",
            duracion = "6 días / 5 noches",
            precio = "$3.200.000",
            categoria = "🏖️ Playa",
            colorCategoria = Turquesa,
            imagen = R.drawable.cancun
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(AzulOceano, Turquesa)
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column {
            Text(
                text = "Aterrizar CO ✈️",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "¡¡Tu próximo destino te espera!!",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun Buscador() {
    var texto by remember { mutableStateOf("") }

    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = { Text("¿A dónde quieres ir?") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Turquesa
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Turquesa,
            unfocusedBorderColor = Color(0xFFE0D6C6)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}

@Composable
fun DestinoCard(
    nombre: String,
    pais: String,
    descripcion: String,
    duracion: String,
    precio: String,
    categoria: String,
    colorCategoria: Color,
    imagen: Int
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Surface(
                shape = RoundedCornerShape(50),
                color = colorCategoria,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = categoria,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = nombre,
                color = AzulOceano,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = pais,
                color = Turquesa,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🕒 $duracion",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = precio,
                    color = NaranjaAtardecer,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NaranjaAtardecer
                    )
                ) {
                    Text("Reservar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AterrizarAppPreview() {
    AterrizarAppTheme {
        AterrizarApp()
    }
}