package ir.pmoslem.treatamovie.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoviesData(moviesList: List<Movie>)

    @Query("SELECT * FROM movies WHERE favoriteStatus = 1")
    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Update
    suspend fun setFavoriteMovie(movie: Movie)

}