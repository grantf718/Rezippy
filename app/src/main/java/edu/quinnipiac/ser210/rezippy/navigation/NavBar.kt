/**
 * NavBar
 */

package edu.quinnipiac.ser210.rezippy.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar (
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    // Change navbar text based on screen
    val navBarText = when (currentRoute?.substringBefore("/")) {
        Screens.HomeScreen.name -> "Recipes"
        Screens.FavoriteScreen.name -> "Favorites"
        Screens.SettingScreen.name -> "Settings"
        else -> "Recipes"
    }

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(navBarText,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W900
                )
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
                            tint = MaterialTheme.colorScheme.secondary,
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
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier
                .fillMaxWidth()
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 3.dp
        )
    }
}