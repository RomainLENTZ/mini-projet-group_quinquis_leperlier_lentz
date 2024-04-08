package com.leperlier.quinquis.lentz.imdb.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Provider(
    val id: Int,
    val name: String,
    val logoPath : String,
) : Parcelable