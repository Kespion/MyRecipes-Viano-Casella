package com.ynov.myrecipes.domain.usecase

import com.ynov.myrecipes.data.repository.MealsRepository
import com.ynov.myrecipes.domain.model.Category
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repo: MealsRepository
) {
    suspend operator fun invoke(): List<Category> = repo.categories()
}