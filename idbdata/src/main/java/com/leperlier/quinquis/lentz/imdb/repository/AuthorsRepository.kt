package com.leperlier.quinquis.lentz.imdb.repository

import com.leperlier.quinquis.lentz.imdb.data.Author
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.datasources.LocalDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineAuthorsDataSource
import com.leperlier.quinquis.lentz.imdb.utils.Result
import javax.inject.Inject

/**
 * La classe permettant de gérer les données de l'application
 */
class AuthorsRepository @Inject internal constructor(
    private val local: LocalDataSource,
    private val online: OnlineAuthorsDataSource
) {

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

    suspend fun getAuthors(): Result<List<Author>> {
        return when(val result = online.getAuthors()) {
            is Result.Succes -> {
                Result.Succes(result.data.results)
            }
            is Result.Error -> result
        }
    }


}