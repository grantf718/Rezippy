package edu.quinnipiac.ser210.rezippy.api.RecipeData.SearchRecipeData

data class UsedIngredient(
    val aisle: String,
    val amount: Double,
    val id: Int,
    val image: String,
    val meta: List<Any>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String,
    val unitLong: String,
    val unitShort: String
)