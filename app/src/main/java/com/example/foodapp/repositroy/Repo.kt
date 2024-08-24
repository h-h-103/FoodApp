package com.example.foodapp.repositroy

import com.example.foodapp.api.ApiService
import com.example.foodapp.db.MealzDao
import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.Mealz
import com.example.foodapp.model.MealzByCategoryResponse
import com.example.foodapp.model.MealzResponse
import javax.inject.Inject

class Repo @Inject constructor(private val apiService: ApiService, private val mealzDao: MealzDao) {

    // Remote data source
    suspend fun getRandomMealFromRemote(): MealzResponse {
         return apiService.getRandomMeal()
    }

    suspend fun getMealDetailsFromRemote(id: String): MealzResponse {
        return apiService.getMealDetails(id)
    }

    suspend fun getMealDetailsFromRemoteLongClick(id: String): MealzResponse {
        return apiService.getMealDetails(id)
    }

    suspend fun getPopularItemsFromRemote(categoryName: String): MealzByCategoryResponse {
        return apiService.getPopularItems(categoryName)
    }

    suspend fun getCategoriesFromRemote(): CategoryResponse {
        return apiService.getCategories()
    }

    suspend fun getMealzByCategoryFromRemote(categoryName: String): MealzByCategoryResponse {
        return apiService.getMealsByCategory(categoryName)
    }

    suspend fun searchMealzFromRemote(searchName: String): MealzResponse {
        return apiService.searchMeals(searchName)
    }

    // Local data source
    suspend fun upsertMealz(mealz: Mealz) {
        mealzDao.upsertMealz(mealz)
    }

    suspend fun deleteMealz(mealz: Mealz) {
        mealzDao.deleteMealz(mealz)
    }

    suspend fun getMealz(): List<Mealz> {
        return mealzDao.getMealz()
    }
}