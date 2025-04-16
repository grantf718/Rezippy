/**
 * Interface for Spoonacular's GET random recipes
 */

package edu.quinnipiac.ser210.rezippy.api

import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import edu.quinnipiac.ser210.rezippy.BuildConfig
import edu.quinnipiac.ser210.rezippy.api.BulkRecipeData.BulkRecipes
import edu.quinnipiac.ser210.rezippy.api.SearchRecipeData.RecipesByIngredient
import retrofit2.http.Query

interface RecipeAPI {
    // Method to get many random recipes
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean = true,    // Limit to licensed content
        @Query("tags") tags: String,                            // Recipe tags for diets/intolerances
        @Query("number") number: Int = 30                       // Recipes returned (1 - 100) default 10
    ): Response<RandomRecipes>

    // Method to search for recipes by ingredients
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") ingredients: String,              // Comma-separated list of ingredients
        @Query("number") number: Int = 10,                      // Recipes returned (1 - 100) default 10
        @Query("limitLicense") limitLicense: Boolean = true,    // Limit to licensed content
        @Query("ranking") ranking: Int = 1,                     // Ranking (1 = max used ingredients, 2 = min missing ingredients)
        @Query("ignorePantry") ignorePantry: Boolean = false    // Whether to ignore pantry ingredients
    ): Response<RecipesByIngredient>

    // Method for bulk request for recipes by IDs
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/informationBulk")
    suspend fun getRecipesByIds(
        @Query("ids") ingredients: String,                               // Comma-separated list of IDs
        @Query("includeNutrition") includeNutrition: Boolean = false,    // Include nutrition information
    ): Response<BulkRecipes>

    companion object {
        const val BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/"

        fun create() : RecipeAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RecipeAPI::class.java)

        }
    }
}