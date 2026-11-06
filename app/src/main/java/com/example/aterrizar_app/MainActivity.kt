package com.example.aterrizar_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aterrizar_app.data.local.AterrizarDatabase
import com.example.aterrizar_app.data.repository.AterrizarRepository
import com.example.aterrizar_app.ui.AterrizarApp
import com.example.aterrizar_app.ui.theme.AterrizarAppTheme
import com.example.aterrizar_app.ui.viewmodel.AterrizarViewModel
import com.example.aterrizar_app.ui.viewmodel.AterrizarViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dao = AterrizarDatabase.getDatabase(applicationContext).aterrizarDao()
        val repository = AterrizarRepository(dao)
        val factory = AterrizarViewModelFactory(repository)

        setContent {
            val viewModel: AterrizarViewModel = viewModel(factory = factory)

            AterrizarAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AterrizarApp(viewModel = viewModel)
                }
            }
        }
    }
}