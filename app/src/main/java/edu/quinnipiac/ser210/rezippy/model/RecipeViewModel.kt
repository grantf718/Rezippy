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
import edu.quinnipiac.ser210.rezippy.api.RecipeData.SearchRecipeData.RecipesByIngredient
import edu.quinnipiac.ser210.rezippy.data.Item
import edu.quinnipiac.ser210.rezippy.data.ItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(private val itemDao: ItemDao) : ViewModel() {
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
                    _randomRecipesResult.value = response
                    _randomRecipesResult.value = response
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

    // Search recipes by ingredients
    fun searchRecipesByIngredients(ingredients: String) {
        viewModelScope.launch {
            try {
                val response = recipeAPI.searchRecipesByIngredients(ingredients = ingredients)

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
}