package com.leperlier.quinquis.lentz.imdb.api.service

import CategoryResponse
import MovieResponse
import com.leperlier.quinquis.lentz.imdb.api.response.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesByCategory(@Query("with_genres") categoryId: Int): Response<MovieResponse>
}