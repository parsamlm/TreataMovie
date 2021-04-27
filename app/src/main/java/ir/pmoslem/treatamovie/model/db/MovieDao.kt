package ir.pmoslem.treatamovie.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoviesData(moviesList: List<Movie>)

    @Query("SELECT * FROM movies WHERE favoriteStatus = 1")
    fun getFavoriteMoviesLiveData(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE favoriteStatus = 1")
    suspend fun getFavoriteMovies(): List<Movie>

    @Update
    suspend fun setFavoriteMovie(movie: Movie)

}