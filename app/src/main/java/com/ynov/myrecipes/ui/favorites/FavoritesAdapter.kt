package com.ynov.myrecipes.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ynov.myrecipes.data.local.FavoriteEntity
import com.ynov.myrecipes.databinding.ItemFavoriteBinding
import com.ynov.myrecipes.ui.common.diffCallback
import java.text.SimpleDateFormat
import java.util.*

class FavoritesAdapter(
    private val onDelete: (FavoriteEntity) -> Unit,
    private val onOpen: (FavoriteEntity) -> Unit
) : ListAdapter<FavoriteEntity, FavoritesAdapter.VH>(diffCallback { it.id }) {

    private val dateFmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    inner class VH(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvName.text = item.name
            tvDate.text = dateFmt.format(Date(item.savedAt))
            Glide.with(ivThumb).load(item.thumb).into(ivThumb)
            btnDelete.setOnClickListener { onDelete(item) }
            root.setOnClickListener { onOpen(item) }
        }
    }
}