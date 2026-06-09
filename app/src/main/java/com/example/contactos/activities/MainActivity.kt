// IA generated: MainActivity moved to 'activities' package and annotated according to guidelines.
package com.example.contactos.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactos.ui.theme.ContactosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // IA generated: Enabling edge to edge to use the full screen space.
        enableEdgeToEdge()
        setContent {
            ContactosTheme {
                // IA generated: Simple layout with a Greeting.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// IA generated: Composable function that shows a text message.
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// IA generated: Preview for the IDE to render the Greeting composable.
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactosTheme {
        Greeting("Android")
    }
}
