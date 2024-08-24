package com.example.foodapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.model.Mealz

@Dao
interface MealzDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMealz(mealz: Mealz)

    @Delete
    suspend fun deleteMealz(mealz: Mealz)

    @Query("SELECT * FROM mealz_table")
    suspend fun getMealz(): List<Mealz>
}