package com.leperlier.quinquis.lentz.imdb.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Serie(
    val id: Int,
    val name: String,
    val overview: String,
    val seasons: Int,
    val episodes: Int,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Float,
    val vote_count: Int,
) : Parcelable