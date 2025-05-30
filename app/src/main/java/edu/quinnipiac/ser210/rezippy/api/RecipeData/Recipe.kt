package edu.quinnipiac.ser210.rezippy.api.RecipeData

import edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData.WinePairing

data class Recipe(
    override val aggregateLikes: Int,
    override val analyzedInstructions: List<Any>,
    override val cheap: Boolean,
    override val creditsText: String,
    override val cuisines: List<Any>,
    override val dairyFree: Boolean,
    override val diets: List<Any>,
    override val dishTypes: List<String>,
    override val extendedIngredients: List<ExtendedIngredient>,
    override val gaps: String,
    override val glutenFree: Boolean,
    override val healthScore: Int,
    override val id: Int,
    override val image: String,
    override val imageType: String,
    override val instructions: String,
    override val ketogenic: Boolean,
    override val license: String,
    override val lowFodmap: Boolean,
    override val occasions: List<Any>,
    override val pricePerServing: Double,
    override val readyInMinutes: Int,
    override val servings: Int,
    override val sourceName: String,
    override val sourceUrl: String,
    override val spoonacularScore: Double,
    override val spoonacularSourceUrl: String,
    override val summary: String,
    override val sustainable: Boolean,
    override val title: String,
    override val vegan: Boolean,
    override val vegetarian: Boolean,
    override val veryHealthy: Boolean,
    override val veryPopular: Boolean,
    override val weightWatcherSmartPoints: Int,
    override val whole30: Boolean,
    override val winePairing: WinePairing?,
    override val cookingMinutes: Int?,
    override val originalId: Any?,
    override val preparationMinutes: Int?
): RecipeInterface