package edu.quinnipiac.ser210.rezippy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.quinnipiac.ser210.rezippy.api.RecipeData.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.navigation.Screens
import edu.quinnipiac.ser210.rezippy.ui.components.RecipeCard

@Composable
fun HomeScreen(
    randomRecipes: RandomRecipes?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            randomRecipes?.recipes?.forEach { recipe ->
                item {
                    RecipeCard(
                        recipe = recipe
                    ) {
                        navController.navigate(route = Screens.DetailScreen.name+"/${recipe.title}")
                    }
                }
            }
        }
    }
}
