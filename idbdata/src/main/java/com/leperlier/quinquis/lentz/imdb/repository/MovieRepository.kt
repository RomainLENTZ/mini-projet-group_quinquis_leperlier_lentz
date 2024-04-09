package com.leperlier.quinquis.lentz.imdb.repository

import androidx.lifecycle.LiveData
import com.leperlier.quinquis.lentz.imdb.api.response.CountryProviders
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Provider
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.datasources.LocalDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineDataSource
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import toCategory
import javax.inject.Inject

/**
 * La classe permettant de gérer les données de l'application
 */
class MovieRepository @Inject internal constructor(
    private val local: LocalDataSource,
    private val online: OnlineDataSource
) {

    /**
     * Récupérer le token permettant de consommer les ressources sur le serveur
     * Le résultat du datasource est converti en instance d'objets publiques
     */
    suspend fun getToken(): Result<Token> {
        return when (val result = online.getToken()) {
            is Result.Succes -> {
                local.saveToken(result.data)
                Result.Succes(result.data)
            }
            is Result.Error -> result
        }
    }

    suspend fun getCategories(): Result<List<Category>> {
        return when(val result = online.getCategories()) {
            is Result.Succes -> {
                val categories = result.data.map {
                    it.toCategory()
                }
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }

    suspend fun getWeekTrendingMovies(): Result<List<Movie>> {
        return when(val result = online.getWeekTrendingMovies()) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getDayTrendingMovies(): Result<List<Movie>> {
        return when(val result = online.getDayTrendingMovies()) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getMoviesByCategory(categoryId: Int): Result<List<Movie>> {
        return when(val result = online.getMoviesByCategory(categoryId)) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getSimilarMovies(movieId: Long): Result<List<Movie>>{
        return when(val result = online.getSimilarMovies(movieId)) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getMovieProviders(movieId: Long): Result<List<Provider>> {
        return online.getMovieProviders(movieId)
    }

    suspend fun addFavorite(favorite: FavoriteEntity) {
        local.addFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: FavoriteEntity) {
        local.removeFavorite(favorite)
    }

    fun isFavorite(movieId: Long): LiveData<Boolean> {
        return local.isFavorite(movieId)
    }
}