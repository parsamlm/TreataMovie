package ir.pmoslem.treatamovie.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("ContentID")
    val contentId: String = "",

    @SerializedName("Title")
    val title: String = "",

    @SerializedName("ThumbImage")
    val thumbImgLink: String = "",

    @SerializedName("ZoneID")
    val zoneId: Int,

    @SerializedName("FavoriteStatus")
    var favoriteStatus: Boolean
)

data class ResultObject(
    @SerializedName("Result")
    val moviesList: MovieObject
)

data class MovieObject(
    @SerializedName("GetContentList")
    val moviesList: List<Movie>
)
