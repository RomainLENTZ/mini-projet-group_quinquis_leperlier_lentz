package com.leperlier.quinquis.lentz.imdb.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Modélise les tokens dans la base de données
 */
@Entity(
    tableName = "favorites"
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val isMovie: Boolean
)