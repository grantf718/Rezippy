package edu.quinnipiac.ser210.rezippy.api.RandomRecipeData

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)