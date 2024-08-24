package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.Mealz
import com.example.foodapp.model.MealzByCategoryResponse
import com.example.foodapp.model.MealzResponse
import com.example.foodapp.repositroy.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealzViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    // Remote data sources
    private val _randomMeal = MutableStateFlow<MealzResponse?>(null)
    val randomMeal: StateFlow<MealzResponse?> = _randomMeal

    private val _mealDetails = MutableStateFlow<MealzResponse?>(null)
    val mealDetails: StateFlow<MealzResponse?> = _mealDetails

    private val _mealDetailsLongClick = MutableStateFlow<MealzResponse?>(null)
    val mealDetailsLongClick: StateFlow<MealzResponse?> = _mealDetailsLongClick

    private val _popularItems = MutableStateFlow<MealzByCategoryResponse?>(null)
    val popularItems: StateFlow<MealzByCategoryResponse?> = _popularItems

    private val _categories = MutableStateFlow<CategoryResponse?>(null)
    val categories: StateFlow<CategoryResponse?> = _categories

    private val _mealzByCategory = MutableStateFlow<MealzByCategoryResponse?>(null)
    val mealzByCategory: StateFlow<MealzByCategoryResponse?> = _mealzByCategory

    private val _searchMealz = MutableStateFlow<MealzResponse?>(null)
    val searchMealz: StateFlow<MealzResponse?> = _searchMealz

    // Local data sources
    private val _getMealz = MutableStateFlow<List<Mealz>?>(null)
    val getMealz: StateFlow<List<Mealz>?> = _getMealz

    // Remote data sources
    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                _randomMeal.value = repo.getRandomMealFromRemote()
            } catch (e: Exception) {
                Log.d("Error", "errorFetchRandomMeal ${e.message}")
            }
        }
    }

    fun fetchMealDetails(id: String) {
        viewModelScope.launch {
            try {
                _mealDetails.value = repo.getMealDetailsFromRemote(id)
            } catch (e: Exception) {
                Log.d("Error", "errorFetchMealDetails ${e.message}")
            }
        }
    }

    fun fetchMealDetailsLongClick(id: String) {
        viewModelScope.launch {
            try {
                _mealDetailsLongClick.value = repo.getMealDetailsFromRemoteLongClick(id)
            } catch (e: Exception) {
                Log.d("Error", "errorFetchMealDetails ${e.message}")
            }
        }
    }

    fun fetchPopularItems(categoryName: String) {
        viewModelScope.launch {
            try {
                _popularItems.value = repo.getPopularItemsFromRemote(categoryName)
            } catch (e: Exception) {
                Log.d("Error", "Error fetching popular items: ${e.message}")
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categories.value = repo.getCategoriesFromRemote()
            } catch (e: Exception) {
                Log.d("Error", "Error fetching categories: ${e.message}")
            }
        }
    }

    fun fetchMealzByCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                _mealzByCategory.value = repo.getMealzByCategoryFromRemote(categoryName)
            } catch (e: Exception) {
                Log.d("Error", "Error fetching mealz by category: ${e.message}")
            }
        }
    }

    fun fetchSearchMealz(searchName: String) {
        viewModelScope.launch {
            try {
                _searchMealz.value = repo.searchMealzFromRemote(searchName)
            } catch (e: Exception) {
                Log.d("Error", "Error fetching search mealz: ${e.message}")
            }
        }
    }

    // Local data sources
    fun upsertMealz(mealz: Mealz) {
        viewModelScope.launch {
            try {
                repo.upsertMealz(mealz)
            } catch (e: Exception) {
                Log.d("Error", "Error upsert mealz: ${e.message}")
            }
        }
    }

    fun deleteMealz(mealz: Mealz) {
        viewModelScope.launch {
            try {
                repo.deleteMealz(mealz)
            } catch (e: Exception) {
                Log.d("Error", "Error delete mealz: ${e.message}")
            }
        }
    }

    fun getMealz() {
        viewModelScope.launch {
            try {
                _getMealz.value = repo.getMealz()
            } catch (e: Exception) {
                Log.d("Error", "Error get mealz: ${e.message}")
            }
        }
    }
}