package com.leperlier.quinquis.lentz.imdb.api.response

import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Video

data class VideoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videos: List<Video>
)
