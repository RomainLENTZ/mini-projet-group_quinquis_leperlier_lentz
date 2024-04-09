package com.leperlier.quinquis.lentz.imdb.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.local.daos.FavoritesDao
import com.leperlier.quinquis.lentz.imdb.local.daos.TokenDao
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.local.entities.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import com.leperlier.quinquis.lentz.imdb.utils.Result

@Singleton
internal class LocalDataSource @Inject constructor(private val tokenDao: TokenDao, private val favoritesDao: FavoritesDao) :
    MovieDataSource {

    override suspend fun getToken(): Result<Token> = withContext(Dispatchers.IO) {
        tokenDao.retrieve()?.let {
            Result.Succes(it.toToken())
        } ?: Result.Error(
            exception = Exception(),
            message = "You should get a token from the API first",
            code = -1
        )
    }

    override suspend fun saveToken(token: Token) {
        withContext(Dispatchers.IO) {
            tokenDao.insert(token.toEntity())
        }
    }

    suspend fun addFavorite(favorite: FavoriteEntity) {
        withContext(Dispatchers.IO){
            favoritesDao.insertFavorite(favorite)
        }
    }

    suspend fun removeFavorite(favorite: FavoriteEntity) {
        withContext(Dispatchers.IO) {
            favoritesDao.deleteFavorite(favorite)
        }
    }

    fun isFavorite(movieId: Long): LiveData<Boolean> {
        return Transformations.map(favoritesDao.getFavoriteById(movieId)) {
            it != null
        }
    }

    suspend fun getFavorites(): List<FavoriteEntity> = withContext(Dispatchers.IO) {
        favoritesDao.getAllFavorites()
    }

}

internal fun Token.toEntity() = TokenEntity(
    expiresAt = this.expiresAt,
    token = this.requestToken
)

internal fun TokenEntity.toToken() = Token(
    expiresAt = this.expiresAt,
    requestToken = this.token
)