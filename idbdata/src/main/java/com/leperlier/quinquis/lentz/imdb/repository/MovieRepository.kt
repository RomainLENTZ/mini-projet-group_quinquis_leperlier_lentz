package com.leperlier.quinquis.lentz.imdb.repository

import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Provider
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.datasources.LocalDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineDataSource
import com.leperlier.quinquis.lentz.imdb.utils.Result
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
                //save the response in the local database
                local.saveToken(result.data)
                //return the response
                Result.Succes(result.data)
            }
            is Result.Error -> result
        }
    }

    suspend fun getCategories(): Result<List<Category>> {
        return when(val result = online.getCategories()) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
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

}