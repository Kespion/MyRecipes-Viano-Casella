package com.ynov.myrecipes.data.api

import com.ynov.myrecipes.data.api.dto.CategoriesDTO
import com.ynov.myrecipes.data.api.dto.MealDetailResponse
import com.ynov.myrecipes.data.api.dto.MealsDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET("categories.php")
    suspend fun getCategories(): CategoriesDTO

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsDTO

    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): MealDetailResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsDTO
}

val api: MealsApi = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(MoshiConverterFactory.create())
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    )
    .build()
    .create(MealsApi::class.java)