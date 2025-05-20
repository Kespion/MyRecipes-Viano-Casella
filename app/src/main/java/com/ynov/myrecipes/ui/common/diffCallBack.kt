package com.ynov.myrecipes.ui.common

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

inline fun <T : Any, K> diffCallback(crossinline keySelector: (T) -> K) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            keySelector(oldItem) == keySelector(newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    }