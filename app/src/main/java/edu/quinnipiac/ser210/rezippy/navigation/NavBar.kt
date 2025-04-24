/**
 * NavBar
 */

package edu.quinnipiac.ser210.rezippy.navigation

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.coerceIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar (
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    recipe: Recipe?
) {
    // Get context (for share button)
    val context = LocalContext.current

    // Change navbar text based on screen
    var navBarText = when (currentRoute?.substringBefore("/")) {
        Screens.HomeScreen.name -> "Recipes"
        Screens.FavoriteScreen.name -> "Favorites"
        Screens.SettingScreen.name -> "Settings"
        Screens.HelpScreen.name -> "Help"
        Screens.DetailScreen.name -> recipe?.title
        else -> "Change this text in NavBar.kt"
    }

    // Default to "Recipe" if recipe title cant be retrieved  
    if(navBarText.isNullOrBlank()){
        navBarText = "Recipe"
    }

    Column { // (For a divider line at the bottom)
        CenterAlignedTopAppBar(
            title = {
                // Box to scale text
                BoxWithConstraints {
                    val maxWidthDp = maxWidth
                    val charCount = navBarText.length.coerceAtLeast(1)
                    val dpPerChar = maxWidthDp / charCount
                    val targetSp = with(LocalDensity.current) { dpPerChar.toSp() }
                    val minFontSize = 18.sp
                    val maxFontSize = 40.sp
                    val fontSize = with(LocalDensity.current) { (maxWidth * 0.10f).toSp() }


                    Text(
                        text = navBarText,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize),
                        fontWeight = FontWeight.W900,
                        textAlign = TextAlign.Center,
                    )
                }
            },
            navigationIcon = {
                // Conditional back navigation from detail screen
                Log.i("Nav Route: ", "$currentRoute")
                if (currentRoute?.substringBefore("/") == Screens.DetailScreen.name) {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
                // Menu icon for NavDrawer if not on DetailScreen
                else {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            },
            actions = {
                if(currentRoute?.substringBefore("/") == Screens.DetailScreen.name){
                    val shareText = recipe?.let {
                        buildString {
                            append("Check out this recipe: ${it.title}\n\n")
                            append("Ingredients:\n")
                            it.extendedIngredients.forEach { ingredient ->
                                append("• ${ingredient.name}\n")
                            }
                            append("\nInstructions:\n")
                            append(it.instructions
                                .replace(Regex("<[^>]*>"), "")
                                .split("\n").forEachIndexed { index, instruction ->
                                    append("${index + 1}.\t${instruction}\n")
                                }
                            )
                        }
                    } ?: "Check out this recipe!"

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = modifier
                .fillMaxWidth()
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.tertiary,
            thickness = 3.dp
        )
    }
}