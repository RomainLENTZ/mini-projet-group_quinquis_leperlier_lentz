package com.leperlier.quinquis.lentz.imdb.api.response

import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Provider
import com.leperlier.quinquis.lentz.imdb.data.Serie

internal data class ProviderResponse(
    val id: Long,
    val results: Map<String, CountryProviders>
)

data class CountryProviders(
    val link: String,
    val flatrate: List<ProviderDetails>? = null
)

data class ProviderDetails(
    @SerializedName("provider_id")
    val id: Int,
    @SerializedName("provider_name")
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String
)