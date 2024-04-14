import com.google.gson.annotations.SerializedName
import com.leperlier.quinquis.lentz.imdb.data.Authors
import com.leperlier.quinquis.lentz.imdb.data.Category

internal data class AuthorsResponse(
    @SerializedName("authors")
    val authors: List<Author>
) {
    data class Author(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}

internal fun AuthorsResponse.Author.toAuthor() : Authors {
    return Authors(id = id, name = name)
}