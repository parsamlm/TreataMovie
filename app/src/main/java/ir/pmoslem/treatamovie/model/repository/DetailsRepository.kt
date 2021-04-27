package ir.pmoslem.treatamovie.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.db.MovieDetails
import ir.pmoslem.treatamovie.model.db.MovieDetailsDao
import ir.pmoslem.treatamovie.model.server.ApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
private val isErrorOccurred: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
private const val TAG = "DetailsRepository"


class DetailsRepository @Inject constructor(
    private val api: ApiService,
    private val movieDao: MovieDao,
    private val movieDetailsDao: MovieDetailsDao
) {
    private val isFavoriteMovie = MutableLiveData<Boolean>()

    init {
        progressBarStatus.postValue(true)
        isErrorOccurred.postValue(false)
    }

    fun getMovieDetailsFromServer(requestId: Long): LiveData<MovieDetails> {
        val movieDetailsLiveData = MutableLiveData<MovieDetails>()
        val innerJson = JsonObject()
        innerJson.addProperty("RequestID", requestId)
        val requestBody = JsonObject()
        requestBody.add("request", innerJson)
        GlobalScope.launch(Dispatchers.IO) {
            try {

                val movieDetailsObject = api.getContentDetail(requestBody)
                val movieDetails = movieDetailsObject.movieDetails
                movieDetailsDao.insertMovieDetailsContent(movieDetails)
                movieDetailsLiveData.postValue(movieDetails)
                progressBarStatus.postValue(false)
                isErrorOccurred.postValue(false)

            } catch (e: IOException) {
                Log.e(TAG, e.toString())
                cancel()
                isErrorOccurred.postValue(true)

            } catch (e: HttpException) {
                Log.e(TAG, e.toString())
                cancel()
                isErrorOccurred.postValue(true)
            }

        }
        return movieDetailsLiveData
    }


    fun isFavoriteMovieExistsInDatabase(movie: Movie): LiveData<Boolean> {
        GlobalScope.launch(Dispatchers.IO) {
            val movies = movieDao.getFavoriteMovies()
            isFavoriteMovie.postValue(movie in movies)
        }
        return isFavoriteMovie
    }

    fun updateMovie(movie: Movie) {
        movie.favoriteStatus = !movie.favoriteStatus
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.setFavoriteMovie(movie)
            val movies = movieDao.getFavoriteMovies()
            isFavoriteMovie.postValue(movie in movies)
        }
    }

    fun getProgressBarStatus(): LiveData<Boolean> = progressBarStatus

    fun isErrorOccurred(): LiveData<Boolean> = isErrorOccurred


}