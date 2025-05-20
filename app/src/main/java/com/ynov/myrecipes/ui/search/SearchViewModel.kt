package com.ynov.myrecipes.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.domain.usecase.SearchMealsUseCase
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMeals: SearchMealsUseCase
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    val state: StateFlow<Resource<List<Meal>>> = queryFlow
        .debounce(300)
        .filter { it.length >= 2 }
        .distinctUntilChanged()
        .flatMapLatest {
            flow {
                emit(Resource.Loading)
                try {
                    emit(Resource.Success(searchMeals(it)))
                } catch (e: Exception) {
                    emit(Resource.Error(e.message ?: "Unknown"))
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)

    fun updateQuery(q: String) {
        queryFlow.value = q
    }
}