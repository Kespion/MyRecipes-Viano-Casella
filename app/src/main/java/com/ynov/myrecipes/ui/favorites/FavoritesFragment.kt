package com.ynov.myrecipes.ui.favorites

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
import com.ynov.myrecipes.databinding.FragmentFavoritesBinding
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val vm: FavoritesViewModel by viewModels()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        adapter = FavoritesAdapter(
            onOpen = { meal ->
                val bundle = bundleOf("mealId" to meal.id)
                findNavController().navigate(R.id.action_favorites_to_mealDetail, bundle)
            },
            onDelete = { meal ->
                vm.delete(meal)
            }
        )
        binding.recyclerFavorites.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { state ->
                binding.progress.isVisible = state is Resource.Loading

                when (state) {
                    is Resource.Success -> {
                        adapter.submitList(state.data)
                        binding.tvEmpty.isVisible = state.data.isEmpty()
                    }
                    is Resource.Error -> Snackbar
                        .make(binding.root, state.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                        .show()
                    Resource.Loading -> {  }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
