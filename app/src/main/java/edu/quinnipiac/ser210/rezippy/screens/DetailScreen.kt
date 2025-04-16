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
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes

@Composable
fun DetailScreen(
    randomRecipes: RandomRecipes?,
    recipeTitle: String?
){
    val recipe = randomRecipes?.recipes?.filter { recipe ->
        recipe.title == recipeTitle
    }

    //TODO: Detail Screen
}