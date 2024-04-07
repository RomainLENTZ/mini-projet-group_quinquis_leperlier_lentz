import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Movie

internal data class MovieResponse(
    @SerializedName("results")
    val results: List<Movie>,

    @SerializedName("page")
    val Page: Int
)