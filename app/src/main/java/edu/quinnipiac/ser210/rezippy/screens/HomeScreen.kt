package edu.quinnipiac.ser210.rezippy.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import edu.quinnipiac.ser210.rezippy.R
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.navigation.Screens
import edu.quinnipiac.ser210.rezippy.ui.theme.textColor

@Composable
fun HomeScreen(
    randomRecipes: RandomRecipes?,
    navController: NavController,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
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

@Composable
fun RecipeCard(
    recipe: Recipe,
    onItemClick: (String) -> Unit = {}
){
    Card(
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.tertiaryContainer), // border color
        colors = CardDefaults.cardColors(
            containerColor = Color.White // entire card (but just the bottom of it shows, where the text is)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable {
                onItemClick(recipe.title)
            }
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
                    .height(180.dp)
            )
            BoxWithConstraints(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            ) {
                // Dynamically scale font size
                val trimmedTitle = recipe.title.trimEnd()
                val charCount = trimmedTitle.length.coerceAtLeast(1)
                val dpPerChar = maxWidth / charCount
                val targetSp = with(LocalDensity.current) { dpPerChar.toSp() }
                val minFontSize: TextUnit = 24.sp // min size
                val maxFontSize: TextUnit = 42.sp // max size
                val fontSize = when {
                    targetSp < minFontSize -> minFontSize
                    targetSp > maxFontSize -> maxFontSize
                    else                   -> targetSp
                }


                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

// For previewing purposes
@Composable
fun TESTRecipeCard(
    recipeName: String
){
    Card(
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.tertiaryContainer), // border color
        colors = CardDefaults.cardColors(
            containerColor = Color.White // entire card (but just the bottom of it shows, where the text is)
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
            Image(
                painter = painterResource(id = R.drawable.sample_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(180.dp)
            )
            BoxWithConstraints(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            ) {

                // Dynamically scale font size
                val charCount = recipeName.length.coerceAtLeast(1)
                val dpPerChar = maxWidth / charCount
                val targetSp = with(LocalDensity.current) { dpPerChar.toSp() }
                val minFontSize: TextUnit = 15.sp
                val maxFontSize: TextUnit = 33.sp
                val fontSize = when {
                    targetSp < minFontSize -> minFontSize
                    targetSp > maxFontSize -> maxFontSize
                    else                   -> targetSp
                }
                Text(
                    text = recipeName,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
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
    AppTheme{
        Column(){
            TESTRecipeCard("Short name")
            TESTRecipeCard("Reeaaaallllly looooong naaaaaaammmmmmmeeeeeee")
        }

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
//    AppTheme { HomeScreen(randomRecipes = null) }
}
