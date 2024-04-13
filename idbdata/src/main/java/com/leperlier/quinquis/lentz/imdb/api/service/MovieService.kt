package com.leperlier.quinquis.lentz.imdb.api.service

import CategoryResponse
import MovieResponse
import com.leperlier.quinquis.lentz.imdb.api.response.ProviderResponse
import com.leperlier.quinquis.lentz.imdb.api.response.TokenResponse
import com.leperlier.quinquis.lentz.imdb.api.response.VideoResponse
import com.leperlier.quinquis.lentz.imdb.data.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("trending/movie/week")
    suspend fun getWeekTrendingMovies(): Response<MovieResponse>

    @GET("trending/movie/day")
    suspend fun getDayTrendingMovies(): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesByCategory(@Query("with_genres") categoryId: Int): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesByAuthor(@Query("with_people") authorId: Int): Response<MovieResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Long): Response<MovieResponse>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(@Path("movie_id") movieId: Long): Response<ProviderResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Long): Response<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") movieId: Int): Response<VideoResponse>

}