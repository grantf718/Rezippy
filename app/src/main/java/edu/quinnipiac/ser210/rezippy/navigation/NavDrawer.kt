/**
 * NavDrawer
 */

package edu.quinnipiac.ser210.rezippy.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val selectedScreen by remember { mutableStateOf(Screens.HomeScreen.name) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet (
                drawerContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ) {
                Column (
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .verticalScroll(rememberScrollState())
                ) {
                    //Drawer title
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Rezippy",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()

                    // Pages section
                    Text(
                        text = "Pages",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Home",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedScreen == Screens.HomeScreen.name) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = null
                            )
                        },
                        selected = selectedScreen == Screens.HomeScreen.name,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.HomeScreen.name)
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Bookmarked",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                //TODO: Bookmarked navigation
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Suggestions",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                //TODO: AI Suggestions screen
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )

                    // Settings
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Settings",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = null
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                //TODO: Settings screen
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Help",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                //TODO: Help Snackbar
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                            unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState,
        content = content
    )
}