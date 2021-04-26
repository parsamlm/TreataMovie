package ir.pmoslem.treatamovie.model.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("ContentID")
    val contentId: Long,

    @SerializedName("Title")
    val title: String = "",

    @SerializedName("ThumbImage")
    val thumbImgLink: String = "",

    @SerializedName("ZoneID")
    val zoneId: Int,

    @SerializedName("FavoriteStatus")
    var favoriteStatus: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(contentId)
        parcel.writeString(title)
        parcel.writeString(thumbImgLink)
        parcel.writeInt(zoneId)
        parcel.writeByte(if (favoriteStatus) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}

data class ResultObject(
    @SerializedName("Result")
    val moviesList: MovieObject
)

data class MovieObject(
    @SerializedName("GetContentList")
    val moviesList: List<Movie>
)
