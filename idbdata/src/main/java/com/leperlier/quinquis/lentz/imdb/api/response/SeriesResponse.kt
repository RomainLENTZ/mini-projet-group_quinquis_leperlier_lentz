import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Serie

internal data class SeriesResponse(
    @SerializedName("results")
    val results: List<Serie>,

    @SerializedName("page")
    val Page: Int,
)