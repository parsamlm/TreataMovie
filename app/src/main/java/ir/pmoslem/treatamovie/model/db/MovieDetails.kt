package ir.pmoslem.treatamovie.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MoviesDetails")
data class MovieDetails(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("ContentID")
    val contentId: Long,

    @SerializedName("Title")
    val title: String,

    @SerializedName("LandscapeImage")
    val landscapeImageUrl: String,

    @SerializedName("Summary")
    val summary: String,

    @SerializedName("FavoriteStatus")
    var favoriteStatus: Boolean
)

data class MovieDetailsObject(
    @SerializedName("Result")
    val movieDetails: MovieDetails
)
