package com.leperlier.quinquis.lentz.imdb.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    @SerializedName("key")
    val key: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("name")
    val name: String
) : Parcelable
