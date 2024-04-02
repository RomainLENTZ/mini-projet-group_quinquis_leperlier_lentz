import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Category

internal data class CategoryResponse(
    @SerializedName("genres")
    val genres: List<Genre>
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}

internal fun CategoryResponse.Genre.toCategory() : Category{
    return Category(id = id, name = name)
}