package edu.quinnipiac.ser210.rezippy.api.RecipeData

data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consitency: String?,
    val consistency: String?,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val name: String,
    val nameClean: String?,
    val original: String,
    val originalName: String,
    val unit: String
)