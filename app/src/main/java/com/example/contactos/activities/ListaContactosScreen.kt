// IA generated: Screen that displays a list of contacts using a LazyColumn.
package com.example.contactos.activities

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
    // IA generated: LazyColumn to efficiently display the list of contacts.
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(contactos) { contacto ->
            ContactoItem(contacto = contacto, onClick = { onContactoClick(contacto) })
            // IA generated: Divider for better visual separation between contacts.
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
        }
    }
}

// IA generated: Component for a single contact item. Displays photo/avatar, name, and phone.
@Composable
fun ContactoItem(contacto: Contacto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // IA generated: Box to hold the avatar/photo.
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            // IA generated: Displaying the contact's photo.
            Image(
                painter = painterResource(id = contacto.fotoRes),
                contentDescription = "Foto de ${contacto.nombre}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // IA generated: If the image was transparent or just an icon, initials could be shown,
            // but for simplicity, we rely on the photoRes provided.
        }

        Spacer(modifier = Modifier.width(16.dp))

        // IA generated: Column for Name and Phone Number text.
        Column {
            Text(
                text = contacto.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = contacto.telefono,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
