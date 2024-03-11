package com.leperlier.quinquis.lentz.imdb.api.service

import CategoryResponse
import com.leperlier.quinquis.lentz.imdb.api.response.TokenResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface MovieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>
}