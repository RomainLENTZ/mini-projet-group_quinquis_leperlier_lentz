package com.leperlier.quinquis.lentz.imdb.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leperlier.quinquis.lentz.imdb.local.daos.TokenDao
import com.leperlier.quinquis.lentz.imdb.local.entities.TokenEntity

/**
 * Modélise la base de données ainsi que les tables de la BDD
 */
@Database(
    entities = [TokenEntity::class],
    version = 1
)
internal abstract class IdbDataBase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}