package com.ynov.myrecipes.ui.meals

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
import com.ynov.myrecipes.databinding.FragmentMealsBinding
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealsFragment : Fragment(R.layout.fragment_meals) {

    private val vm: MealsViewModel by viewModels()

    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MealsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealsBinding.bind(view)

        val category = requireArguments().getString("categoryName") ?: ""
        vm.load(category)

        adapter = MealsAdapter { onMealClicked(it) }
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { render(it) }
        }
    }

    private fun render(state: Resource<List<Meal>>) = with(binding) {
        progress.isVisible = state is Resource.Loading

        when (state) {
            is Resource.Success -> adapter.submitList(state.data)
            is Resource.Error   ->
                Snackbar.make(root, state.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                    .show()
            Resource.Loading    -> Unit
        }
    }

    private fun onMealClicked(meal: Meal) {
        val bundle = bundleOf("mealId" to meal.id)
        findNavController().navigate(R.id.action_meals_to_detail, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
