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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
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
    direccion: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Función para abrir el mapa con validación de aplicación disponible
    val abrirMapa: (String) -> Unit = { dir ->
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(dir)}")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        // Verificamos si hay una app disponible para manejar el intent
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "No se encontró una aplicación de mapas compatible", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para solicitar el permiso de llamadas (ACTION_CALL)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefono"))
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "El permiso de llamada es necesario para la llamada directa", Toast.LENGTH_SHORT).show()
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

            Spacer(modifier = Modifier.height(16.dp))

            // Estructura solicitada para la dirección y el ícono del mapa interactivo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.clickable { abrirMapa(direccion) }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = direccion)
                IconButton(onClick = { abrirMapa(direccion) }) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ver en mapa"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Fila con los tres botones de acción rápida
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. Botón de Llamada Directa (Requiere permiso)
                Button(
                    onClick = {
                        val permissionCheck = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CALL_PHONE
                        )
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefono"))
                            context.startActivity(intent)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text("Directa", fontSize = 11.sp)
                }

                // 2. Botón de Marcador (ACTION_DIAL, no requiere permiso)
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telefono"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text("Marcador", fontSize = 11.sp)
                }

                // 3. Botón de Mensaje (SMS)
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$telefono"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text("Mensaje", fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para Compartir contacto
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Contacto: $nombre")
                        putExtra(Intent.EXTRA_TEXT, "Nombre: $nombre\nTeléfono: $telefono\nDirección: $direccion")
                    }
                    val chooser = Intent.createChooser(intent, "Compartir contacto vía")
                    context.startActivity(chooser)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Compartir contacto")
            }
        }
    }
}
