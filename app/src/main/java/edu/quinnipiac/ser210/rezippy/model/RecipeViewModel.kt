/**
 * RecipeViewModel for handling recipe network requests
 */

package edu.quinnipiac.ser210.rezippy.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.rezippy.api.BulkRecipeData.BulkRecipes
import edu.quinnipiac.ser210.rezippy.api.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeAPI
import edu.quinnipiac.ser210.rezippy.api.SearchRecipeData.RecipesByIngredient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel : ViewModel() {
    private val recipeAPI = RecipeAPI.create()

    // LiveData for random recipes
    private val _randomRecipesResult = MutableLiveData<Response<RandomRecipes>>()
    val randomRecipesResult: LiveData<Response<RandomRecipes>> = _randomRecipesResult
    private var isRecipesFetched = false    // Used to prevent repeated https requests

    // LiveData for search by ingredients
    private val _recipesByIngredientResult = MutableLiveData<Response<RecipesByIngredient>>()
    val recipesByIngredientResult: LiveData<Response<RecipesByIngredient>> = _recipesByIngredientResult

    // LiveData for bulk recipes by ID
    private val _bulkRecipesResult = MutableLiveData<Response<BulkRecipes>>()
    val bulkRecipesResult: LiveData<Response<BulkRecipes>> = _bulkRecipesResult

    // Get random recipes upon initialization
    init {
        Log.d("Init: ", "ViewModel initializing")
        fetchRandomRecipes()
    }

    // Fetch random recipes
    fun fetchRandomRecipes(number: Int = 2) {
        if (isRecipesFetched) return

        viewModelScope.launch {
            try {
                val response = recipeAPI.getRandomRecipes(number = number)

                if (response.isSuccessful) {
                    Log.d("API response: ", response.body().toString())
                    _randomRecipesResult.value = response
                }
                else {
                    Log.d("Network Error","Failed to load data: ${response.code()}, ${response.errorBody()?.string()}")
                }
                isRecipesFetched = true;
            } catch (e : Exception) {
                e.message?.let { Log.d("network error", it) }
            }
        }
    }

    // Search recipes by ingredients
    fun searchRecipesByIngredients(number: Int, ingredients: String) {
        viewModelScope.launch {
            try {
                val response = recipeAPI.searchRecipesByIngredients(number = number, ingredients = ingredients)

                if (response.isSuccessful) {
                    Log.d("API response: ", response.body().toString())
                    _recipesByIngredientResult.value = response
                }
                else {
                    Log.d("network error","Failed to load data")
                }
            } catch (e : Exception) {
                e.message?.let { Log.d("network error", it) }
            }
        }
    }

    // Fetch bulk recipes by ID
    fun fetchBulkRecipes(ids: String, includeNutrition: Boolean = false) {
        viewModelScope.launch {
            try {
                val response = recipeAPI.getRecipesByIds(ids = ids)

                if (response.isSuccessful) {
                    Log.d("API response: ", response.body().toString())
                    _bulkRecipesResult.value = response
                }
                else {
                    Log.d("network error","Failed to load data")
                }
            } catch (e : Exception) {
                e.message?.let { Log.d("network error", it) }
            }
        }
    }
}