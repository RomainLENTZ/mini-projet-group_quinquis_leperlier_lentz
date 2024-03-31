package com.leperlier.quinquis.lentz.imdb.datasources
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.utils.Result

interface MovieDataSource {
    suspend fun getToken(): Result<Token>
    suspend fun saveToken(token: Token)
}