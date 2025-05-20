package com.ynov.myrecipes.domain.model

data class MealDetail(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumb: String,
    val ingredients: List<Ingredient>,
    val nutriScorePercent: Float
)