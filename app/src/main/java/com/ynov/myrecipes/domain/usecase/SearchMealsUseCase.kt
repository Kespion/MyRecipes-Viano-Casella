package com.ynov.myrecipes.domain.usecase

import com.ynov.myrecipes.data.repository.MealsRepository
import com.ynov.myrecipes.domain.model.Meal
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val repo: MealsRepository
) {
    suspend operator fun invoke(query: String): List<Meal> = repo.search(query)
}