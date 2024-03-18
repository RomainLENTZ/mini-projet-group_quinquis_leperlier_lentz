package com.leperlier.quinquis.lentz.imdb.datasources

import MovieResponse
import com.leperlier.quinquis.lentz.imdb.api.response.toToken
import com.leperlier.quinquis.lentz.imdb.api.service.MovieService
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
internal class OnlineDataSource @Inject constructor(private val service: MovieService): MovieDataSource {

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
        service.getCategories().let { response ->
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

    suspend fun getMovies(): Result<MovieResponse> = safeCall {
        service.getMovies().let { response ->
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
