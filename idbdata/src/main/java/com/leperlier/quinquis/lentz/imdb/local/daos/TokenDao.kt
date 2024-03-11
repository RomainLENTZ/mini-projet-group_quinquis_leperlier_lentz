package com.leperlier.quinquis.lentz.imdb.local.daos

import androidx.room.*
import com.leperlier.quinquis.lentz.imdb.local.entities.TokenEntity

@Dao
internal interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TokenEntity)

    @Query("SELECT * from idb_token LIMIT 1")
    fun retrieve(): TokenEntity?

    @Query("DELETE FROM idb_token")
    fun delete()
}