package com.ynov.myrecipes.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.myrecipes.domain.model.Category
import com.ynov.myrecipes.domain.usecase.GetCategoriesUseCase
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val state: StateFlow<Resource<List<Category>>> = _state

    init { load() }

    fun load() = viewModelScope.launch {
        try {
            _state.value = Resource.Loading
            _state.value = Resource.Success(getCategories())
        } catch (e: Exception) {
            _state.value = Resource.Error(e.message ?: "Unknown")
        }
    }
}