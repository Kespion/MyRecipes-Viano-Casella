package com.ynov.myrecipes.di

import android.content.Context
import androidx.room.Room
import com.ynov.myrecipes.data.api.MealsApi
import com.ynov.myrecipes.data.local.MealsDatabase
import com.ynov.myrecipes.data.local.FavoritesDAO
import com.ynov.myrecipes.data.remote.MealsRemoteDataSource
import com.ynov.myrecipes.data.repository.FavoritesRepository
import com.ynov.myrecipes.data.repository.MealsRepository
import com.ynov.myrecipes.util.Mapper
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    // ------------------ Network ------------------ //
    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .build()

    @Singleton
    @Provides
    fun provideMealsApi(retrofit: Retrofit): MealsApi = retrofit.create(MealsApi::class.java)

    // ------------------ Database ------------------ //
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext ctx: Context): MealsDatabase = Room.databaseBuilder(
        ctx,
        MealsDatabase::class.java,
        MealsDatabase.NAME
    ).build()

    @Provides
    fun provideFavoritesDao(db: MealsDatabase): FavoritesDAO = db.favoritesDAO()

    // ------------------ Data Sources & Repos ------------------ //
    @Singleton
    @Provides
    fun provideRemoteDataSource(api: MealsApi): MealsRemoteDataSource = MealsRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideMealsRepository(remote: MealsRemoteDataSource): MealsRepository = MealsRepository(remote)

    @Singleton
    @Provides
    fun provideFavoritesRepository(dao: FavoritesDAO): FavoritesRepository = FavoritesRepository(dao)

    // ------------------ Coroutine Dispatcher ------------------ //
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    // ------------------ Mapper (stateless) ------------------ //
    @Provides
    fun provideMapper() = Mapper
}