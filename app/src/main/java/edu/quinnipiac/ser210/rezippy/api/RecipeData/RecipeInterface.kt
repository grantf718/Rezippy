package edu.quinnipiac.ser210.rezippy.api.RecipeData

import edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData.WinePairing

interface RecipeInterface {
    val aggregateLikes: Int
    val analyzedInstructions: List<Any>
    val cheap: Boolean
    val cookingMinutes: Int?
    val creditsText: String
    val cuisines: List<Any>
    val dairyFree: Boolean
    val diets: List<Any>
    val dishTypes: List<String>
    val extendedIngredients: List<ExtendedIngredient>
    val gaps: String
    val glutenFree: Boolean
    val healthScore: Any
    val id: Int
    val image: String
    val imageType: String
    val instructions: Any
    val ketogenic: Boolean?
    val license: Any
    val lowFodmap: Boolean
    val occasions: List<Any>
    val originalId: Any?
    val preparationMinutes: Int?
    val pricePerServing: Double
    val readyInMinutes: Int
    val servings: Int
    val sourceName: String
    val sourceUrl: String
    val spoonacularScore: Double
    val spoonacularSourceUrl: String?
    val summary: String
    val sustainable: Boolean
    val title: String
    val vegan: Boolean
    val vegetarian: Boolean
    val veryHealthy: Boolean
    val veryPopular: Boolean
    val weightWatcherSmartPoints: Int
    val whole30: Boolean?
    val winePairing: WinePairing?
}