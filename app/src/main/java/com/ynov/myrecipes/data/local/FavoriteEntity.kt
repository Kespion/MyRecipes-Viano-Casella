package com.ynov.myrecipes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumb: String,
    val savedAt: Long = System.currentTimeMillis()
)