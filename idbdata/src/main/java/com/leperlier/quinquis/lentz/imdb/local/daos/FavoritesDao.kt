package com.leperlier.quinquis.lentz.imdb.local.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites WHERE id = :movieId LIMIT 1")
    fun getFavoriteById(movieId: Long): LiveData<FavoriteEntity?>
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<FavoriteEntity>

    @Insert
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)
}