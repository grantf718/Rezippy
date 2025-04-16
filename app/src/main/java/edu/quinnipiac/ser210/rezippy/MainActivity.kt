package edu.quinnipiac.ser210.rezippy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.quinnipiac.ser210.rezippy.navigation.Navigation
import edu.quinnipiac.ser210.rezippy.ui.theme.RezippyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RezippyApp {
                Navigation()
            }
        }
    }
}

@Composable
fun RezippyApp(content: @Composable () -> Unit) {
    RezippyTheme {
        content()
    }
}
