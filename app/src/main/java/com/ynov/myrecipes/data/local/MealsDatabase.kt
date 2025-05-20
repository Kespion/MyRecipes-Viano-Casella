package com.ynov.myrecipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDAO

    companion object { const val NAME = "myrecipes.db" }
}