package com.ynov.myrecipes.domain.usecase

import com.ynov.myrecipes.data.repository.MealsRepository
import com.ynov.myrecipes.domain.model.MealDetail
import javax.inject.Inject

class GetMealDetailsUseCase @Inject constructor(
    private val repo: MealsRepository
) {
    suspend operator fun invoke(id: String): MealDetail = repo.detail(id)
}