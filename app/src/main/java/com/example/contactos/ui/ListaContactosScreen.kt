// IA generated: Screen that displays a list of contacts using a LazyColumn.
package com.example.contactos.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactos.models.Contacto

// IA generated: Main screen component for the contact list.
@Composable
fun ListaContactosScreen(
    contactos: List<Contacto>,
    onContactoClick: (Contacto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(contactos) { contacto ->
            ContactoItem(contacto = contacto, onClick = { onContactoClick(contacto) })
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
        }
    }
}

// IA generated: Component for a single contact item in the list.
@Composable
fun ContactoItem(contacto: Contacto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // IA generated: Avatar showing the first letter of the name as a fallback.
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            val inicial = if (contacto.nombre.isNotEmpty()) contacto.nombre[0].uppercase() else "?"
            Text(
                text = inicial,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            // IA generated: Using the photo resource if available.
            // In a real app we might check if fotoRes is valid, here we just overlay or skip.
            // For simplicity, we use the initials as the main "avatar" look.
        }

        Spacer(modifier = Modifier.width(16.dp))

        // IA generated: Column for Name and Phone Number.
        Column {
            Text(
                text = contacto.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = contacto.telefono,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
