package edu.quinnipiac.ser210.rezippy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme

@Composable
fun HelpScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary,
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {

            // Font sizing constants
            val bigHeader = 38.sp
            val header = 27.sp
            val body = 24.sp

            item{
                // Header
                Text(
                    text = "Tired of online recipe searching?",
                    fontSize = bigHeader,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "You're in the right place. Here's how to get cooking.",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // Browsing recipes
                Text(
                    text = "Browse Recipes",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Head to the Recipes screen to explore a variety of delicious meals. Scroll through and tap on any recipe card to dive into the full details.",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // Details page
                Text(
                    text = "View Recipe Details",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "On the recipe detail page, you’ll find everything you need — a description, a full list of ingredients, and step-by-step instructions to guide your cooking.",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // Favorites
                Text(
                    text = "Save Your Favorites",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Found something tasty? Tap the favorite icon while viewing a recipe to save it. Then, revisit your saved meals anytime from the favorites screen!",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // AI
                Text(
                    text = "Get Recipe Suggestions",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Not sure what to cook? Visit the Suggestions screen to chat with our built-in AI assistant! Ask for meal ideas, substitutions, or tips — it’s like having a sous-chef in your pocket.",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // Light/Dark mode
                Text(
                    text = "Customization",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Visit the Settings screen to toggle between Light and Dark Mode, so you can cook in comfort day or night.",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(5.dp)
                )
            }

            item {
                // Credits
                Text(
                    text = "See the Credits",
                    fontSize = header,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Want to know who cooked up Rezippy? Tap Credits in the settings to meet the creators!",
                    fontSize = body,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview
@Composable
fun HelpPreview(){
    AppTheme{
        HelpScreen()
    }
}