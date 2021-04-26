package ir.pmoslem.treatamovie.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieDetailsContent(movie: Movie)

}