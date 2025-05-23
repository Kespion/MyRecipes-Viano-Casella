package com.ynov.myrecipes.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ynov.myrecipes.R
import com.ynov.myrecipes.databinding.FragmentSearchBinding
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.ui.common.Resource
import com.ynov.myrecipes.ui.meals.MealsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val vm: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MealsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        adapter = MealsAdapter { meal -> onMealClicked(meal) }
        binding.recyclerSearch.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { state ->
                binding.progress.isVisible = state is Resource.Loading
                when (state) {
                    is Resource.Success -> {
                        adapter.submitList(state.data)
                        binding.tvEmpty.isVisible = state.data.isEmpty()
                    }
                    is Resource.Error ->
                        Snackbar.make(binding.root, state.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                            .show()
                    Resource.Loading -> Unit
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                vm.updateQuery(query)
            } else {
                Snackbar.make(binding.root, "Veuillez saisir un terme de recherche", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onMealClicked(meal: Meal) {
        val bundle = bundleOf("mealId" to meal.id)
        findNavController().navigate(R.id.action_searchFragment_to_mealDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
