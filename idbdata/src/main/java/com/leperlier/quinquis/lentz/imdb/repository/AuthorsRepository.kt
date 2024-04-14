package com.leperlier.quinquis.lentz.imdb.repository

import com.leperlier.quinquis.lentz.imdb.data.Authors
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.data.Video
import com.leperlier.quinquis.lentz.imdb.datasources.LocalDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineAuthorsDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineDataSource
import com.leperlier.quinquis.lentz.imdb.datasources.OnlineSeriesDataSource
import com.leperlier.quinquis.lentz.imdb.utils.Result
import toAuthor
import toCategory
import java.io.SerializablePermission
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

    suspend fun getAuthors(): Result<List<Authors>> {
        return when(val result = online.getAuthors()) {
            is Result.Succes -> {
                val categories = result.data.map {
                    it.toAuthor()
                }
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }


}