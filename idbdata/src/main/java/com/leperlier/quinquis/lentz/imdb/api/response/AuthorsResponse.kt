import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Author

internal data class AuthorsResponse(
    @SerializedName("results")
    val results: List<Author>,

    @SerializedName("page")
    val Page: Int
)
