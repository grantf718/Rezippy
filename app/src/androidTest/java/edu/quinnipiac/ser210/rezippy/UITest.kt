package edu.quinnipiac.ser210.rezippy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.quinnipiac.ser210.rezippy.api.RecipeData.ExtendedIngredient
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Measures
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Metric
import edu.quinnipiac.ser210.rezippy.api.RecipeData.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Us
import edu.quinnipiac.ser210.rezippy.screens.HomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class UITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun recipeListDisplaysCorrectly() {
        val randomRecipes = RandomRecipes(
            recipes = List(3) { index ->
                sampleRecipe.copy(title = "Coconut Dinner $index")
            }
        )

        composeTestRule.setContent {
            val navController = rememberNavController()

            var isDarkMode by remember { mutableStateOf(false) }
            RezippyAppTheme(
                useDarkTheme = isDarkMode,
                onToggleTheme = { isDarkMode = !isDarkMode }
            ) {
                HomeScreen(
                    randomRecipes = randomRecipes,
                    navController = navController
                )
            }
        }

        randomRecipes.recipes.forEachIndexed { index, recipe ->
            composeTestRule
                .onNodeWithText(recipe.title)
                .assertExists()
        }
    }
}

val sampleRecipe = Recipe(
    aggregateLikes = 1,
    analyzedInstructions = listOf("test"),
    cheap = false,
    creditsText = "credits",
    cuisines = listOf("cuisines"),
    dairyFree = false,
    diets = listOf("diet"),
    dishTypes = listOf("a dish"),
    extendedIngredients = listOf(ExtendedIngredient(
        aisle = "1",
        amount = 2.0,
        consitency = null,
        consistency = null,
        id = 1,
        image = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.polproduct.nl%2Fproduct%2Fcoconut-milk%2F&psig=AOvVaw3MnDU9MsCqaCTrEbFlbhSY&ust=1746127011253000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPjplpa8gI0DFQAAAAAdAAAAABAJ",
        measures = Measures(
            metric = Metric(
                amount = 1.0,
                unitLong = "kilomile",
                unitShort = "km"
            ),
            us = Us(
                amount = 1.0,
                unitLong = "foot",
                unitShort = "ft"
            )
        ),
        meta = listOf("metas"),
        name = "Coconut",
        nameClean = null,
        original = "idk",
        originalName = "Coconut",
        unit = "1"
    )),
    gaps = "gaps",
    glutenFree = false,
    healthScore = 1,
    id = 1,
    image = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.polproduct.nl%2Fproduct%2Fcoconut-milk%2F&psig=AOvVaw3MnDU9MsCqaCTrEbFlbhSY&ust=1746127011253000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPjplpa8gI0DFQAAAAAdAAAAABAJ",
    imageType = ".png",
    instructions = "instructions",
    ketogenic = false,
    license = "license",
    lowFodmap = false,
    occasions = listOf("everyday"),
    pricePerServing = 1.0,
    readyInMinutes = -1,
    servings = 100,
    sourceName = "insert source here",
    sourceUrl = "insert source website here",
    spoonacularScore = 5.0,
    spoonacularSourceUrl = "",
    summary = "Good recipe",
    sustainable = false,
    title = "Coconut Dinner",
    vegan = false,
    vegetarian = false,
    veryHealthy = false,
    veryPopular = true,
    weightWatcherSmartPoints = 0,
    whole30 = false,
    winePairing = null
)