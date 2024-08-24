package com.example.foodapp.di

import android.app.Application
import androidx.room.Room
import com.example.foodapp.db.MealzDB
import com.example.foodapp.db.MealzDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDB(application: Application): MealzDB {
        return Room.databaseBuilder(application.applicationContext, MealzDB::class.java, "Mealz_DB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(mealzDB: MealzDB): MealzDao {
        return mealzDB.mealzDao()
    }
}