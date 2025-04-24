package edu.quinnipiac.ser210.rezippy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe

@Composable
fun DetailScreen(
    recipe: Recipe?,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxSize()
    ){
        if (recipe == null) {
            Text(
                text = "Couldn't find recipe",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                item {
                    RecipeImage(
                        image = recipe.image,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
                    RecipeDetails(
                        recipe = recipe,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeImage(image: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(320.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(RoundedCornerShape(28.dp))
    ){
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RecipeDetails(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .wrapContentHeight()
            .padding(4.dp)
    ){
        // Recipe title
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        // Recipe summary
        Text(
            // Remove html tags from summary
            text = recipe.summary.replace(Regex("<[^>]*>"), ""),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(4.dp)
        )

        // Recipe ingredients
        Text(
            text = "Ingredients:",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        Text(
            text = buildString {
                recipe.extendedIngredients.forEach {ingredient ->
                    append("\u2022\t${ingredient.name}\n")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(4.dp)
        )

        // Recipe Instructions
        Text(
            text = "Instructions:",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        Text(
            text = buildString {
                recipe.instructions
                    .replace(Regex("<[^>]*>"), "")
                    .split("\n").forEachIndexed {index, instruction ->
                    append("${index + 1}.\t${instruction}\n")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
    }
}