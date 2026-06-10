package com.example.contactos.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleContactoScreen(
    nombre: String,
    telefono: String,
    fotoRes: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Launcher para solicitar el permiso de llamadas
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Lanza el Intent solo si el resultado es true
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$telefono")
            }
            context.startActivity(intent)
        } else {
            // Muestra un Toast indicando que el permiso es necesario
            Toast.makeText(context, "El permiso de llamada es necesario para realizar la llamada directa", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Contacto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = fotoRes),
                    contentDescription = "Foto de $nombre",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = nombre,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = telefono,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    // Verifica primero con ContextCompat.checkSelfPermission
                    val permissionCheck = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    )

                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        // Si el permiso ya está concedido -> lanza Intent(Intent.ACTION_CALL)
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefono"))
                        context.startActivity(intent)
                    } else {
                        // Si no está concedido -> llama al launcher para solicitarlo
                        permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Llamar")
            }
        }
    }
}
