/**
 * NavController for handling navigation between screens
 */

package edu.quinnipiac.ser210.rezippy.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModel
import edu.quinnipiac.ser210.rezippy.screens.DetailScreen
import edu.quinnipiac.ser210.rezippy.screens.HomeScreen

@Composable
fun Navigation(recipeViewModel: RecipeViewModel){
    // NavController
    val navController = rememberNavController()

    // Access Recipe ViewModel data
    val randomRecipesResult = recipeViewModel.randomRecipesResult.observeAsState()
    val randomRecipesResponse = randomRecipesResult.value    // Random recipes to populate HomeScreen
    val bulkRecipesResult = recipeViewModel.bulkRecipesResult.observeAsState()
    val bulkRecipesResponse = bulkRecipesResult.value        // Bulk recipe requests used by FavoriteScreen

    // Navigation Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NavDrawer(
        drawerState = drawerState,
        navController = navController,
        scope = scope
    ) {
        Scaffold (
            topBar = {
                NavBar(
                    navController = navController,
                    scope = scope,
                    drawerState = drawerState
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.HomeScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screens.HomeScreen.name) {
                    HomeScreen(
                        randomRecipes = randomRecipesResponse?.body(),
                        navController = navController
                    )
                }
                composable(
                    Screens.DetailScreen.name+"/{name}",
                    arguments = listOf(navArgument(name = "name") {type = NavType.StringType})
                ) { backStackEntry ->
                    DetailScreen(
                        // Pass only the clicked recipe to the DetailScreen
                        recipe = randomRecipesResponse?.body()?.recipes?.filter { recipe ->
                            recipe.title == backStackEntry.arguments?.getString("name")
                        }?.firstOrNull()
                    )
                }
                //TODO: Additional Screens
            }
        }
    }
}