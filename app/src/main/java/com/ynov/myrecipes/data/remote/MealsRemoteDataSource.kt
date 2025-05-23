package com.ynov.myrecipes.data.remote

import com.ynov.myrecipes.data.api.MealsApi
import com.ynov.myrecipes.data.api.dto.CategoryDTO
import com.ynov.myrecipes.data.api.dto.MealDTO
import com.ynov.myrecipes.data.api.dto.MealDetailResponse
import javax.inject.Inject

class MealsRemoteDataSource @Inject constructor(
    private val api: MealsApi
) {
    suspend fun fetchCategories(): List<CategoryDTO> = api.getCategories().categories
    suspend fun fetchMeals(category: String): List<MealDTO> = api.getMealsByCategory(category).meals
    suspend fun fetchMealDetail(id: String): MealDetailResponse = api.getMealDetails(id)
    suspend fun searchMeals(query: String): List<MealDTO> = api.searchMeals(query).meals
}