package com.ynov.myrecipes.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealDetailDTO(
    @Json(name = "idMeal") val id: String,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strDrinkAlternate") val drinkAlt: String? = null,
    @Json(name = "strCategory") val category: String? = null,
    @Json(name = "strArea") val area: String? = null,
    @Json(name = "strInstructions") val instructions: String? = null,
    @Json(name = "strMealThumb") val thumb: String? = null,
    @Json(name = "strTags") val tags: String? = null,
    @Json(name = "strYoutube") val youtube: String? = null,

    @Json(name = "strIngredient1")  val ing1: String? = null,
    @Json(name = "strIngredient2")  val ing2: String? = null,
    @Json(name = "strIngredient3")  val ing3: String? = null,
    @Json(name = "strIngredient4")  val ing4: String? = null,
    @Json(name = "strIngredient5")  val ing5: String? = null,
    @Json(name = "strIngredient6")  val ing6: String? = null,
    @Json(name = "strIngredient7")  val ing7: String? = null,
    @Json(name = "strIngredient8")  val ing8: String? = null,
    @Json(name = "strIngredient9")  val ing9: String? = null,
    @Json(name = "strIngredient10") val ing10: String? = null,
    @Json(name = "strIngredient11") val ing11: String? = null,
    @Json(name = "strIngredient12") val ing12: String? = null,
    @Json(name = "strIngredient13") val ing13: String? = null,
    @Json(name = "strIngredient14") val ing14: String? = null,
    @Json(name = "strIngredient15") val ing15: String? = null,
    @Json(name = "strIngredient16") val ing16: String? = null,
    @Json(name = "strIngredient17") val ing17: String? = null,
    @Json(name = "strIngredient18") val ing18: String? = null,
    @Json(name = "strIngredient19") val ing19: String? = null,
    @Json(name = "strIngredient20") val ing20: String? = null,

    @Json(name = "strMeasure1")  val mes1: String? = null,
    @Json(name = "strMeasure2")  val mes2: String? = null,
    @Json(name = "strMeasure3")  val mes3: String? = null,
    @Json(name = "strMeasure4")  val mes4: String? = null,
    @Json(name = "strMeasure5")  val mes5: String? = null,
    @Json(name = "strMeasure6")  val mes6: String? = null,
    @Json(name = "strMeasure7")  val mes7: String? = null,
    @Json(name = "strMeasure8")  val mes8: String? = null,
    @Json(name = "strMeasure9")  val mes9: String? = null,
    @Json(name = "strMeasure10") val mes10: String? = null,
    @Json(name = "strMeasure11") val mes11: String? = null,
    @Json(name = "strMeasure12") val mes12: String? = null,
    @Json(name = "strMeasure13") val mes13: String? = null,
    @Json(name = "strMeasure14") val mes14: String? = null,
    @Json(name = "strMeasure15") val mes15: String? = null,
    @Json(name = "strMeasure16") val mes16: String? = null,
    @Json(name = "strMeasure17") val mes17: String? = null,
    @Json(name = "strMeasure18") val mes18: String? = null,
    @Json(name = "strMeasure19") val mes19: String? = null,
    @Json(name = "strMeasure20") val mes20: String? = null,

    @Json(name = "strSource") val source: String? = null,
    @Json(name = "dateModified") val dateModified: String? = null
)