package com.example.contactos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactos.activities.DetalleContactoScreen
import com.example.contactos.activities.ListaContactosScreen
import com.example.contactos.ui.theme.ContactosTheme
import com.example.contactos.utils.ContactosProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactosTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val contactos = ContactosProvider.getSampleContactos()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "lista",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("lista") {
                ListaContactosScreen(
                    contactos = contactos,
                    onContactoClick = { contacto ->
                        navController.navigate("detalle/${contacto.nombre}/${contacto.telefono}/${contacto.fotoRes}")
                    }
                )
            }
            composable(
                route = "detalle/{nombre}/{telefono}/{fotoRes}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("telefono") { type = NavType.StringType },
                    navArgument("fotoRes") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                val telefono = backStackEntry.arguments?.getString("telefono") ?: ""
                val fotoRes = backStackEntry.arguments?.getInt("fotoRes") ?: 0
                
                DetalleContactoScreen(
                    nombre = nombre,
                    telefono = telefono,
                    fotoRes = fotoRes,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
