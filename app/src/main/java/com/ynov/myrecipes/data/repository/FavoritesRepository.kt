package com.ynov.myrecipes.data.repository

import com.ynov.myrecipes.data.local.FavoriteEntity
import com.ynov.myrecipes.data.local.FavoritesDAO
import com.ynov.myrecipes.domain.model.MealDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val dao: FavoritesDAO,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val favorites: Flow<List<FavoriteEntity>> = dao.observe()

    suspend fun toggle(meal: MealDetail) = withContext(ioDispatcher) {
        val current = favorites.first()
        if (current.any { it.id == meal.id }) dao.deleteById(meal.id) else
            dao.upsert(FavoriteEntity(meal.id, meal.name, meal.thumb))
    }
}