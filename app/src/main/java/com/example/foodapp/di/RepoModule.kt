package com.example.foodapp.di

import com.example.foodapp.api.ApiService
import com.example.foodapp.db.MealzDao
import com.example.foodapp.repositroy.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideRepo(apiService: ApiService, mealzDao: MealzDao): Repo {
        return Repo(apiService, mealzDao)
    }
}