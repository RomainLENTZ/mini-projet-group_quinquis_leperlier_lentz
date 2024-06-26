package com.leperlier.quinquis.lentz.imdb.api.service

import CategoryResponse
import MovieResponse
import SeriesResponse
import com.leperlier.quinquis.lentz.imdb.api.response.TokenResponse
import com.leperlier.quinquis.lentz.imdb.api.response.VideoResponse
import com.leperlier.quinquis.lentz.imdb.data.Serie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SerieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/tv/list")
    suspend fun getSeriesCategories(): Response<CategoryResponse>

    @GET("tv/{tv_id}")
    suspend fun getSeriesDetails(@Path("tv_id") seriesId: Long): Response<SeriesResponse>

    @GET("discover/tv")
    suspend fun getSeriesByCategory(@Query("with_genres") categoryId: Int): Response<SeriesResponse>

    @GET("trending/tv/week")
    suspend fun getWeekTrendingSeries(): Response<SeriesResponse>

    @GET("trending/tv/day")
    suspend fun getDayTrendingSeries(): Response<SeriesResponse>

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarSeries(@Path("tv_id") serieId: Int): Response<SeriesResponse>

    @GET("tv/{tv_id}")
    suspend fun getSerieById(@Path("tv_id") serieId: Long): Response<Serie>

    @GET("tv/{tv_id}/videos")
    suspend fun getSerieTrailer(@Path("tv_id") serieId: Long): Response<VideoResponse>

}