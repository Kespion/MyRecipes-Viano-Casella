package com.ynov.myrecipes.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.myrecipes.domain.model.MealDetail
import com.ynov.myrecipes.domain.usecase.GetMealDetailsUseCase
import com.ynov.myrecipes.domain.usecase.ToggleFavoriteUseCase
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getDetail: GetMealDetailsUseCase,
    val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<MealDetail>>(Resource.Loading)
    val state: StateFlow<Resource<MealDetail>> = _state

    fun load(id: String) = viewModelScope.launch {
        _state.value = Resource.Loading
        _state.value = try {
            Resource.Success(getDetail(id))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown")
        }
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        (state.value as? Resource.Success)?.data?.let { toggleFavorite(it) }
    }
}