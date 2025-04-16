package edu.quinnipiac.ser210.rezippy.api.RandomRecipeData

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)