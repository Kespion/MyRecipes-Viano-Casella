package com.ynov.myrecipes.domain.usecase

import com.ynov.myrecipes.data.repository.FavoritesRepository
import com.ynov.myrecipes.domain.model.MealDetail
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repo: FavoritesRepository
) {
    suspend operator fun invoke(meal: MealDetail) = repo.toggle(meal)
}