/**
 * Rezippy
 * Authors: Jean LaFrance, Grant Foody
 * Application to make finding/saving recipes simple
 */

package edu.quinnipiac.ser210.rezippy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import edu.quinnipiac.ser210.rezippy.data.FavoritesDatabase
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModel
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModelFactory
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme
import edu.quinnipiac.ser210.rezippy.navigation.Navigation

class MainActivity : ComponentActivity() {
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Database
        val database = Room.databaseBuilder(
            applicationContext,
            FavoritesDatabase::class.java,
            "item_database"
        ).build()
        val itemDao = database.itemDao()

        // Viewmodel with dependency injection
        recipeViewModel = ViewModelProvider(
            this,
            RecipeViewModelFactory(itemDao)
        )[RecipeViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            RezippyApp(
                useDarkTheme = isDarkMode,
                onToggleTheme = { isDarkMode = !isDarkMode }
            ) {
                Navigation(
                    recipeViewModel,
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}

@Composable
fun RezippyApp(
    useDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    content: @Composable () -> Unit
) {
    AppTheme(darkTheme = useDarkTheme) {
        content()
    }
}
