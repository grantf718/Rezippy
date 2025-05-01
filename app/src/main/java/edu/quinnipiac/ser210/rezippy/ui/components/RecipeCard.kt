package edu.quinnipiac.ser210.rezippy.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil3.compose.AsyncImage
import edu.quinnipiac.ser210.rezippy.R
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.api.RecipeData.RecipeInterface
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme

@Composable
fun RecipeCard(
    recipe: RecipeInterface,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {}
){
    Card(
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.tertiary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
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
                    else -> targetSp
                }

                Text(
                    text = trimmedTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize),
                    color = MaterialTheme.colorScheme.onSecondary,
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