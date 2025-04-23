package edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData

data class ProductMatche(
    val averageRating: Double,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val link: String,
    val price: String,
    val ratingCount: Int,
    val score: Double,
    val title: String
)