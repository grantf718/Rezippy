package edu.quinnipiac.ser210.rezippy.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme

@Composable
fun SettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
){
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ){
            // Light/Dark Mode
            OutlinedButton(
                onClick = onToggleTheme,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text("Toggle Light / Dark Mode")
            }
        }
    }
}


@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light"
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun SettingsScreenPreview() {
    AppTheme {
        SettingScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            onToggleTheme = {}
        )
    }
}
