package com.ynov.myrecipes.util

import com.ynov.myrecipes.data.api.dto.CategoryDTO
import com.ynov.myrecipes.data.api.dto.MealDTO
import com.ynov.myrecipes.data.api.dto.MealDetailDTO
import com.ynov.myrecipes.domain.model.Category
import com.ynov.myrecipes.domain.model.Ingredient
import com.ynov.myrecipes.domain.model.Meal
import com.ynov.myrecipes.domain.model.MealDetail

object Mapper {

    fun dtoToCategory(dto: CategoryDTO): Category = Category(
        id = dto.id,
        name = dto.name,
        thumb = dto.thumb
    )

    fun dtoToMeal(dto: MealDTO): Meal = Meal(
        id = dto.id,
        name = dto.name,
        thumb = dto.thumb
    )

    fun dtoToMealDetail(dto: MealDetailDTO): MealDetail {
        val raws = listOf(
            dto.ing1 to dto.mes1,  dto.ing2 to dto.mes2,   dto.ing3 to dto.mes3,   dto.ing4 to dto.mes4,
            dto.ing5 to dto.mes5,  dto.ing6 to dto.mes6,   dto.ing7 to dto.mes7,   dto.ing8 to dto.mes8,
            dto.ing9 to dto.mes9,  dto.ing10 to dto.mes10, dto.ing11 to dto.mes11, dto.ing12 to dto.mes12,
            dto.ing13 to dto.mes13, dto.ing14 to dto.mes14, dto.ing15 to dto.mes15, dto.ing16 to dto.mes16,
            dto.ing17 to dto.mes17, dto.ing18 to dto.mes18, dto.ing19 to dto.mes19, dto.ing20 to dto.mes20
        )

        val ingredients = raws.filter { !it.first.isNullOrBlank() }
            .map { (ing, mes) -> Ingredient(ing!!.trim(), mes?.trim() ?: "") }

        val calories = ingredients.size * 50
        val nutriPercent = ((calories / 1000f).coerceIn(0f, 1f) * 100f)

        return MealDetail(
            id = dto.id,
            name = dto.name,
            category = dto.category ?: "",
            area = dto.area ?: "",
            instructions = dto.instructions ?: "",
            thumb = dto.thumb ?: "",
            ingredients = ingredients,
            nutriScorePercent = nutriPercent
        )
    }
}