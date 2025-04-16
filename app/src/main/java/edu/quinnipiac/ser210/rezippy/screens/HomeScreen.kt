package edu.quinnipiac.ser210.rezippy.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.quinnipiac.ser210.rezippy.R
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.ui.theme.RezippyTheme

@Composable
fun HomeScreen(
    randomRecipes: RandomRecipes?
){
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            randomRecipes?.recipes?.forEach { recipe ->
                item {
                    RecipeCard(recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe){
    Card(
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primaryContainer), // border color
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary // entire card (but just the bottom of it shows, where the text is)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(28.dp))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(5f)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(2f)
            ){
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun RecipeCardPreview(){
    RezippyTheme{
        //Not sure how we are going to preview this yet
        //RecipeCard()
    }
}


@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light"
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun Preview(){
    RezippyTheme{
        HomeScreen(randomRecipes = null)
    }
}
