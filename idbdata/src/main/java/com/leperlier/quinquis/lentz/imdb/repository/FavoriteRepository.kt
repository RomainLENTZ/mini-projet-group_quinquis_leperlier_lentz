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
class FavoriteRepository @Inject internal constructor(
    private val local: LocalDataSource,
) {

    suspend fun addFavorite(favorite: FavoriteEntity) {
        local.addFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: FavoriteEntity) {
        local.removeFavorite(favorite)
    }

    fun isFavorite(movieId: Long): LiveData<Boolean> {
        return local.isFavorite(movieId)
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>>{
        return local.getFavorites()
    }
}