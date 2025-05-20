package com.ynov.myrecipes.ui.categories

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
import com.ynov.myrecipes.databinding.FragmentCategoryListBinding
import com.ynov.myrecipes.domain.model.Category
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryListFragment : Fragment(R.layout.fragment_category_list) {

    private val vm: CategoriesViewModel by viewModels()

    // --- ViewBinding ---
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    // --- Adapter ---
    private lateinit var adapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCategoryListBinding.bind(view)

        // 1. Adapter avec callback de clic
        adapter = CategoryAdapter { onCategoryClicked(it) }
        binding.recyclerView.adapter = adapter

        // 2. Observe l’état du ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest(::render)
        }
    }

    /** Rend la vue selon l’état (loading / data / error). */
    private fun render(state: Resource<List<Category>>) = with(binding) {

        // 1. Affiche ou cache le loader
        progress.isVisible = state is Resource.Loading

        when (state) {
            is Resource.Success -> adapter.submitList(state.data)
            is Resource.Error   -> Snackbar.make(root, state.message ?: "Erreur inconnue", Snackbar.LENGTH_LONG).show()
            Resource.Loading    -> Unit
        }
    }

    /** Navigation vers la liste des recettes d’une catégorie. */
    private fun onCategoryClicked(category: Category) {
        val bundle = bundleOf("categoryName" to category.name)
        findNavController().navigate(R.id.action_categories_to_meals, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}