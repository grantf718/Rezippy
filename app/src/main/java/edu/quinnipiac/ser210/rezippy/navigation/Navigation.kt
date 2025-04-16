/**
 * NavController for handling navigation between screens
 */

package edu.quinnipiac.ser210.rezippy.navigation

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeAPI
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModel
import edu.quinnipiac.ser210.rezippy.screens.DetailScreen
import edu.quinnipiac.ser210.rezippy.screens.HomeScreen
import retrofit2.Response

@Composable
fun Navigation(){
    // NavController
    val navController = rememberNavController()

    // Recipe ViewModel
    val recipeViewModel: RecipeViewModel = viewModel()
    val randomRecipesResult = recipeViewModel.randomRecipesResult.observeAsState()
    val randomRecipesResponse = randomRecipesResult.value    // Random recipes to populate HomeScreen

    Scaffold (
        topBar = {
            NavBar(
                navController = navController,
                modifier = Modifier
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

//Nav bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Conditional back navigation based on current and previous routes
    val backStackEntry = navController.currentBackStackEntry
    val previousBackStackEntry = navController.previousBackStackEntry
    val currentRoute = backStackEntry?.destination?.route
    val previousRoute = previousBackStackEntry?.destination?.route

    CenterAlignedTopAppBar(
        title = {
            Text("Recipes",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            // Conditional backnavigation
            //TODO: Back navigation between DetailScreen and BookmarkedScreen
            if (currentRoute == Screens.DetailScreen.name) {
                when (previousRoute) {
                    Screens.DetailScreen.name -> IconButton(
                        onClick = { navController.popBackStack(Screens.HomeScreen.name, inclusive = true) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                    else -> Log.d("Navigation Error","Failed to find valid screen route in backstack")
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
    )
}