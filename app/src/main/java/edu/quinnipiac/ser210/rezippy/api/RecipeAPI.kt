/**
 * Interface for Spoonacular's GET random recipes
 */

package edu.quinnipiac.ser210.rezippy.api

import edu.quinnipiac.ser210.rezippy.api.RecipeData.RandomRecipeData.RandomRecipes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import edu.quinnipiac.ser210.rezippy.BuildConfig
import edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData.BulkRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeData.SearchRecipeData.RecipesByIngredient
import retrofit2.http.Query

interface RecipeAPI {
    // Method to get random recipes
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int     // Recipes returned (1 - 100) each recipe returned counts as request
    ): Response<RandomRecipes>

    // Method to search for recipes by ingredients
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") ingredients: String,              // Comma-separated list of ingredients
        @Query("number") number: Int = 1,                       // Recipes returned (1 - 100) default 10
        @Query("ranking") ranking: Int = 1,                     // Ranking (1 = max used ingredients, 2 = min missing ingredients)
        @Query("ignorePantry") ignorePantry: Boolean = true    // Whether to ignore pantry ingredients
    ): Response<RecipesByIngredient>

    // Method for bulk request for recipes by IDs
    @Headers(
        // API_KEY stored in local.properties
        "x-rapidapi-key: ${BuildConfig.API_KEY}",
        "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/informationBulk")
    suspend fun getRecipesByIds(
        @Query("ids") ids: String,                               // Comma-separated list of IDs
    ): Response<BulkRecipes>

    companion object {
        const val BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/"

        fun create() : RecipeAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RecipeAPI::class.java)

        }
    }
}