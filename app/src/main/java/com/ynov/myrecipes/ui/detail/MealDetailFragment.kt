package com.ynov.myrecipes.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ynov.myrecipes.R
import com.ynov.myrecipes.databinding.FragmentMealDetailBinding
import com.ynov.myrecipes.domain.model.MealDetail
import com.ynov.myrecipes.ui.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailFragment : Fragment(R.layout.fragment_meal_detail) {

    private val vm: MealDetailViewModel by viewModels()

    private var _binding: FragmentMealDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealDetailBinding.bind(view)

        val mealId = requireArguments().getString("mealId") ?: ""
        vm.load(mealId)

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { render(it) }
        }
    }

    private fun render(state: Resource<MealDetail>) = with(binding) {
        progress.isVisible = state is Resource.Loading

        when (state) {
            is Resource.Success -> bindDetail(state.data)
            is Resource.Error -> Snackbar
                .make(root, state.message ?: "Unknown error", Snackbar.LENGTH_LONG)
                .show()
            Resource.Loading -> {  }
        }
    }

    private fun bindDetail(detail: MealDetail) = with(binding) {
        tvName.text = detail.name
        tvCategory.text = detail.category
        tvArea.text = detail.area
        Glide.with(ivPhoto).load(detail.thumb).into(ivPhoto)

        tvInstructions.text = detail.instructions

        nutriScoreGauge.percent = (detail.nutriScorePercent)

        tvIngredients.text = detail.ingredients
            .joinToString(separator = "\n") { "${it.name}: ${it.measure}" }

        btnFavorite.setOnClickListener {
            vm.onFavoriteClicked()
            Snackbar.make(root, "Favorite updated", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
