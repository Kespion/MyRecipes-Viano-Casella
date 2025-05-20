package com.ynov.myrecipes.ui.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ynov.myrecipes.databinding.ItemMealBinding
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.ui.common.diffCallback

class MealsAdapter(
    private val onClick: (Meal) -> Unit
) : ListAdapter<Meal, MealsAdapter.VH>(diffCallback { it.id }) {

    inner class VH(val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvName.text = item.name
            Glide.with(ivThumb).load(item.thumb).into(ivThumb)
            root.setOnClickListener { onClick(item) }
        }
    }
}