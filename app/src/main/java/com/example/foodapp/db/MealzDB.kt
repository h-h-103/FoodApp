package com.example.foodapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.model.Mealz

@Database(entities = [Mealz::class], version = 1)
@TypeConverters(Convertor::class)
abstract class MealzDB : RoomDatabase() {
    abstract fun mealzDao(): MealzDao
}