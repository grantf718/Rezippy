/**
 * RecipeViewModel for handling recipe network requests
 */

package edu.quinnipiac.ser210.rezippy.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.rezippy.api.RecipeData.BulkRecipeData.BulkRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeData.RandomRecipeData.RandomRecipes
import edu.quinnipiac.ser210.rezippy.api.RecipeAPI
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.api.RecipeData.RecipeInterface
import edu.quinnipiac.ser210.rezippy.api.RecipeData.SearchRecipeData.RecipesByIngredient
import edu.quinnipiac.ser210.rezippy.api.RecipeData.SingleRecipe
import edu.quinnipiac.ser210.rezippy.data.Item
import edu.quinnipiac.ser210.rezippy.data.ItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(private val itemDao: ItemDao) : ViewModel() {
    private val recipeAPI = RecipeAPI.create()

    // LiveData for random recipes
    private val _randomRecipesResult = MutableLiveData<List<RecipeInterface>>()
    val randomRecipesResult: LiveData<List<RecipeInterface>> = _randomRecipesResult
    private var isRecipesFetched = false    // Used to prevent repeated https requests

    // LiveData for search by ingredients
    private val _recipesByIngredient = MutableLiveData<List<SingleRecipe>>()
    val recipesByIngredient: LiveData<List<SingleRecipe>> = _recipesByIngredient

    // LiveData for bulk recipes by ID
    private val _bulkRecipesResult = MutableLiveData<Response<BulkRecipes>>()
    val bulkRecipesResult: LiveData<Response<BulkRecipes>> = _bulkRecipesResult

    // StateFlow to track user favorited recipes
    private val _favoriteRecipes = MutableStateFlow<List<Item?>>(arrayListOf())
    val favoriteRecipes get() = _favoriteRecipes.asStateFlow()

    // Get random recipes upon initialization
    init {
        Log.d("Init: ", "ViewModel initializing")
        fetchRandomRecipes()
        fetchFavoriteRecipes()
    }

    // Fetch random recipes
    fun fetchRandomRecipes(number: Int = 2) {
        if (isRecipesFetched) return

        viewModelScope.launch {
            try {
                val response = recipeAPI.getRandomRecipes(number = number)

                if (response.isSuccessful) {
                    Log.d("API Random Request: ", "Success")
                    _randomRecipesResult.value = response.body()?.recipes
                }
                else {
                    Log.d("Network Error","Failed to load data: ${response.code()}, ${response.errorBody()?.string()}")
                }
                isRecipesFetched = true;
            } catch (e : Exception) {
                e.message?.let { Log.e("network error", it) }
            }
        }
    }

    // Search recipe by ingredients
    fun searchRecipesByIngredients(ingredients: String) {
        if (ingredients.isEmpty()) {
            return
        }

        viewModelScope.launch {
            try {
                // Call on API endpoint to get a list of possible recipes
                val response = recipeAPI.searchRecipesByIngredients(ingredients = ingredients)

                if (response.isSuccessful) {
                    Log.d("API response: ", response.body().toString())
                    //Call on another API endpoint to get specific recipe details for given ID
                    val recipeId = response.body()?.firstOrNull()?.id ?: -1
                    if (recipeId == -1) {
                        return@launch
                    }
                    val singleRecipeResponse = recipeAPI.getSingleRecipe(recipeId)

                    if (singleRecipeResponse.isSuccessful) {
                        Log.d("Fetched Recipe:", singleRecipeResponse.body().toString())

                        // Update livedata
                        val currentRecipes = _recipesByIngredient.value.orEmpty().toMutableList()
                        singleRecipeResponse.body()?.let { currentRecipes.add(it) }
                        _recipesByIngredient.value = currentRecipes

                        // Add recipe to randomRecipeResult so its navigable
                        val currentRandomRecipes = _randomRecipesResult.value.orEmpty().toMutableList()
                        singleRecipeResponse.body()?.let { currentRandomRecipes.add(it) }
                        _randomRecipesResult.value = currentRandomRecipes
                    } else {
                        Log.d("Network Error", "Failed to fetch recipe(id:${recipeId}) details: ${singleRecipeResponse.errorBody()?.string()}")
                    }
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
    fun fetchBulkRecipes(ids: String) {
        if (ids.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val response = recipeAPI.getRecipesByIds(ids = ids)

                    if (response.isSuccessful) {
                        Log.d("API Bulk Request: ", "Success")
                        _bulkRecipesResult.value = response
                    }
                    else {
                        Log.d("network error","Failed to load data: ${response.code()}, ${response.errorBody()?.string()}")
                    }
                } catch (e : Exception) {
                    e.message?.let { Log.d("network error", it) }
                }
            }
        }
    }

    // Calls a bulk fetch request using favorited items in database
    fun fetchFavoriteRecipes() {
        viewModelScope.launch {
            _favoriteRecipes.value = itemDao.getAllItems()
            fetchBulkRecipes(_favoriteRecipes.value.map { it!!.id }.joinToString(","))
        }
    }

    fun saveRecipe(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteRecipe(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun getRecipeById(id: Int) {
        viewModelScope.launch {
            itemDao.getItemById(id)
        }
    }

    fun clearRecipesByIngredient() {
        _recipesByIngredient.value = emptyList()
    }
}