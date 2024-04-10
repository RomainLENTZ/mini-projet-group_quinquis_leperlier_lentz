package com.leperlier.quinquis.lentz.imdb.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leperlier.quinquis.lentz.imdb.local.daos.FavoritesDao
import com.leperlier.quinquis.lentz.imdb.local.daos.TokenDao
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.local.entities.TokenEntity

/**
 * Modélise la base de données ainsi que les tables de la BDD
 */
@Database(entities = [FavoriteEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}