package com.ynov.myrecipes.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDTO(
    @Json(name = "idCategory") val id: String,
    @Json(name = "strCategory") val name: String,
    @Json(name = "strCategoryThumb") val thumb: String,
    @Json(name = "strCategoryDescription") val description: String? = null
)