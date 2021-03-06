package com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.mvvmrecipeapp.domain.model.Recipe
import com.codingwithmitch.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.codingwithmitch.mvvmrecipeapp.repository.RecipeRepository
import com.codingwithmitch.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

@ExperimentalCoroutinesApi
class RecipeViewModel
@ViewModelInject
constructor(
    private val recipeRepository: RecipeRepository,
    private @Named("auth_token") val token: String,
): ViewModel(){

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    // has the recipe been loaded?
    var hasLoaded: Boolean = false

    fun onTriggerEvent(event: RecipeEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetRecipeEvent -> {
                        if(!hasLoaded) getRecipe(event.id)
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
                loading.value = false
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        loading.value = true
        hasLoaded = true

        // simulate a delay to show loading
        delay(1000)

        val recipe = recipeRepository.get(token=token, id=id)
        this.recipe.value = recipe
    }
}




















