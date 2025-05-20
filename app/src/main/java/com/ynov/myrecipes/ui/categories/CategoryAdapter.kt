package com.ynov.myrecipes.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ynov.myrecipes.databinding.ItemCategoryBinding
import com.ynov.myrecipes.domain.model.Category
import com.ynov.myrecipes.ui.common.diffCallback

class CategoryAdapter(
    private val onClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.VH>(diffCallback { it.id }) {

    inner class VH(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvName.text = item.name
            Glide.with(ivThumb).load(item.thumb).into(ivThumb)
            root.setOnClickListener { onClick(item) }
        }
    }
}