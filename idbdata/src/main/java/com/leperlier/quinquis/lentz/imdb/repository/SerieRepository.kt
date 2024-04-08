package com.leperlier.quinquis.lentz.imdb.repository

import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.datasources.LocalDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineSeriesDataSource
import com.leperlier.quinquis.lentz.imdb.utils.Result
import toCategory
import java.io.SerializablePermission
import javax.inject.Inject

/**
 * La classe permettant de gérer les données de l'application
 */
class SerieRepository @Inject internal constructor(
    private val local: LocalDataSource,
    private val online: OnlineSeriesDataSource
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
                val categories = result.data.map {
                    it.toCategory()
                }
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }

    suspend fun getSeriesByCategory(categoryId: Int): Result<List<Serie>> {
        return when(val result = online.getSeriesByCategory(categoryId)) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getSimilarSeries(serieId: Int): Result<List<Serie>>{
        return when(val result = online.getSimilarSeries(serieId)) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getDayTrendingSeries(): Result<List<Serie>>{
        return when(val result = online.getDayTrendingSeries()) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

    suspend fun getWeekTrendingSeries(): Result<List<Serie>>{
        return when(val result = online.getWeekTrendingSeries()) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }

}