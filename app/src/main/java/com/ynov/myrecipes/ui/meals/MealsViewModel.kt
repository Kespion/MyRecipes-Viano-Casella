package com.ynov.myrecipes.ui.meals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.domain.usecase.GetMealsUseCase
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val getMeals: GetMealsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Meal>>>(Resource.Loading)
    val state: StateFlow<Resource<List<Meal>>> = _state

    fun load(category: String) = viewModelScope.launch {
        _state.value = Resource.Loading
        _state.value = try {
            Resource.Success(getMeals(category))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown")
        }
    }
}