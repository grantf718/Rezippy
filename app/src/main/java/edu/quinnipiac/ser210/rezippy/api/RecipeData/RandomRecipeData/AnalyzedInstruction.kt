package edu.quinnipiac.ser210.rezippy.api.RecipeData.RandomRecipeData

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)