package edu.quinnipiac.ser210.rezippy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.quinnipiac.ser210.rezippy.screens.HomeScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != Screens.HomeScreen.name

    HomeScreen()
}