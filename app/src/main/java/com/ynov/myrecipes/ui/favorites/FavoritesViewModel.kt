package com.ynov.myrecipes.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.myrecipes.data.local.FavoriteEntity
import com.ynov.myrecipes.data.repository.FavoritesRepository
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repo: FavoritesRepository
) : ViewModel() {

    val state: StateFlow<Resource<List<FavoriteEntity>>> = repo.favorites
        .map { Resource.Success(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Resource.Loading)

    fun delete(entity: FavoriteEntity) = viewModelScope.launch { repo.toggle(entity.toDomain()) }
}

private fun FavoriteEntity.toDomain() = com.ynov.myrecipes.domain.model.MealDetail(
    id = id,
    name = name,
    category = "",
    area = "",
    instructions = "",
    thumb = thumb,
    ingredients = emptyList(),
    nutriScorePercent = 0f
)