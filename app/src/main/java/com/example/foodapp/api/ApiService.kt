package com.example.foodapp.api

import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.MealzByCategoryResponse
import com.example.foodapp.model.MealzResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomMeal(): MealzResponse

    @GET("lookup.php?")
    suspend fun getMealDetails(@Query("i") id: String): MealzResponse

    @GET("filter.php?")
    suspend fun getPopularItems(@Query("c") categoryName: String): MealzByCategoryResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php?")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): MealzByCategoryResponse

    @GET("search.php?")
    suspend fun searchMeals(@Query("s") searchName: String): MealzResponse
}