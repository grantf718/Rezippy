/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */

package edu.quinnipiac.ser210.rezippy.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.quinnipiac.ser210.rezippy.data.ItemDao

// ViewModelFactory used to inject dependencies into ViewModel
// https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
class RecipeViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val application = checkNotNull(RecipeViewModel::class.java)

        return RecipeViewModel(itemDao) as T
    }
}