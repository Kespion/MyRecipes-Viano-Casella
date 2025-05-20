package com.ynov.myrecipes.data.repository

import com.ynov.myrecipes.data.api.dto.MealDetailDTO
import com.ynov.myrecipes.data.remote.MealsRemoteDataSource
import com.ynov.myrecipes.domain.model.Category
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.domain.model.MealDetail
import com.ynov.myrecipes.util.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val remote: MealsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun categories(): List<Category> = withContext(ioDispatcher) {
        remote.fetchCategories().map(Mapper::dtoToCategory)
    }

    suspend fun meals(category: String): List<Meal> = withContext(ioDispatcher) {
        remote.fetchMeals(category).map(Mapper::dtoToMeal)
    }

    suspend fun detail(id: String): MealDetail = withContext(ioDispatcher) {
        Mapper.dtoToMealDetail(getMealDetail(remote.fetchMealDetail(id).meals, id))
    }

    suspend fun search(query: String): List<Meal> = withContext(ioDispatcher) {
        remote.searchMeals(query).map(Mapper::dtoToMeal)
    }

    private fun getMealDetail(list: List<MealDetailDTO>, id: String): MealDetailDTO {
        for (meal in list) {
            if (meal.id == id) {
                return meal
            }
        }
        throw IllegalArgumentException("Meal with id $id not found")
    }
}