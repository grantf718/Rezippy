package edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)