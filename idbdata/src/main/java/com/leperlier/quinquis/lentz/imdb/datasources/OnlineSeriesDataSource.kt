package com.leperlier.quinquis.lentz.imdb.datasources

import MovieResponse
import SeriesResponse
import com.leperlier.quinquis.lentz.imdb.api.response.toToken
import com.leperlier.quinquis.lentz.imdb.api.service.MovieService
import com.leperlier.quinquis.lentz.imdb.api.service.SerieService
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.utils.Result
import com.leperlier.quinquis.lentz.imdb.utils.safeCall // Assurez-vous que ceci est correctement importé.
import retrofit2.Response
import javax.inject.Inject

/**
 * Manipule les données de l'application en utilisant un web service
 * Cette classe est interne au module, ne peut être initialisé ou exposé aux autres composants
 * de l'application.
 */
internal class OnlineSeriesDataSource @Inject constructor(private val service: SerieService): SerieDataSource {

    /**
     * Récupérer le token du serveur.
     * @return [Result<Token>]
     * Si [Result.Succes], tout s'est bien passé.
     * Sinon, une erreur est survenue.
     */
    override suspend fun getToken(): Result<Token> = safeCall {
        service.getToken().parse().let { parsedResponse ->
            when (parsedResponse) {
                is Result.Succes -> Result.Succes(parsedResponse.data.toToken())
                is Result.Error -> parsedResponse
            }
        }
    }

    /**
     * Fonction d'extension pour traiter les réponses HTTP.
     */
    internal fun <T : Any> Response<T>.parse(): Result<T> {
        return if (isSuccessful) {
            body()?.let {
                Result.Succes(it)
            } ?: Result.Error(
                exception = NoDataException(),
                message = "Aucune donnée",
                code = 404
            )
        } else {
            Result.Error(
                exception = Exception(),
                message = message(),
                code = code()
            )
        }
    }

    class NoDataException: Exception()

    override suspend fun saveToken(token: Token) {
        TODO("I don't know how to save a token, the local datasource probably does")
    }

    suspend fun getCategories(): Result<List<CategoryResponse.Genre>> = safeCall {
        service.getSeriesCategories().let { response ->
            if (response.isSuccessful) {
                Result.Succes(response.body()!!.genres)
            } else {
                Result.Error(
                    exception = Exception("Erreur lors de la récupération des catégories"),
                    message = response.message(),
                    code = response.code()
                )
            }
        }
    }

    suspend fun getSeriesByCategory(categoryId: Int): Result<SeriesResponse> = safeCall {
        service.getSeriesByCategory(categoryId).let { response ->
            if (response.isSuccessful) {
                Result.Succes(response.body()!!)
            } else {
                Result.Error(
                    exception = Exception("Erreur lors de la récupération des séries par genres"),
                    message = response.message(),
                    code = response.code()
                )
            }
        }
    }

    suspend fun getSimilarSeries(serieId: Int): Result<SeriesResponse> = safeCall {
        service.getSimilarSeries(serieId).let { response ->
            if (response.isSuccessful) {
                Result.Succes(response.body()!!)
            } else {
                Result.Error(
                    exception = Exception("Erreur lors de la récupération des films similaires"),
                    message = response.message(),
                    code = response.code()
                )
            }
        }
    }

    suspend fun getWeekTrendingSeries(): Result<SeriesResponse> = safeCall {
        service.getWeekTrendingSeries().let { response ->
            if (response.isSuccessful) {
                Result.Succes(response.body()!!)
            } else {
                Result.Error(
                    exception = Exception("Erreur lors de la récupération des films par genres"),
                    message = response.message(),
                    code = response.code()
                )
            }
        }
    }

    suspend fun getDayTrendingSeries(): Result<SeriesResponse> = safeCall {
        service.getDayTrendingSeries().let { response ->
            if (response.isSuccessful) {
                Result.Succes(response.body()!!)
            } else {
                Result.Error(
                    exception = Exception("Erreur lors de la récupération des films par genres"),
                    message = response.message(),
                    code = response.code()
                )
            }
        }
    }

}
