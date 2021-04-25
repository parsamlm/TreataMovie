package ir.pmoslem.treatamovie.model.repository

import android.provider.Settings
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import ir.pmoslem.treatamovie.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val ERROR_TAG: String = "ResponseError"
private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

class ContentRepository @Inject constructor(private val api: ApiService, private val movieDao: MovieDao) {

    init {
        progressBarStatus.postValue(true)
    }

    fun getContentListFromServer(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val innerJson = JsonObject()
                innerJson.addProperty("RequestType", 2)
                innerJson.add("RequestId", JsonNull.INSTANCE)
                innerJson.addProperty("PageSize", 10)
                innerJson.addProperty("PageIndex", 1)
                innerJson.addProperty("OrderBy", "createdate")
                innerJson.addProperty("Order", "desc")
                innerJson.get("request")

                val requestBody = JsonObject()
                requestBody.add("request", innerJson)

                val response = api.getContentList(requestBody)
                if (response.isSuccessful){
                    val resultObject: ResultObject? = response.body()
                    val movieObject: MovieObject? = resultObject?.moviesList
                    val movieList: List<Movie>? = movieObject?.moviesList
                    if (movieList != null){
                        movieDao.insertMoviesData(movieList)
                        progressBarStatus.postValue(false)
                    }
                }
            }catch (e : Exception){
                cancel()
                Log.e(ERROR_TAG, e.message.toString())
                progressBarStatus.postValue(true)
            }
        }
    }

    fun getContentListFromDatabase(): LiveData<List<Movie>> = movieDao.getAllMovies()

    fun getFavoriteContentListFromDatabase(): LiveData<List<Movie>> = movieDao.getFavoriteMovies()

    fun getProgressBarStatus(): LiveData<Boolean> = progressBarStatus

    fun onFavoriteButtonClicked(movie: Movie) {
        movie.favoriteStatus = !movie.favoriteStatus
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.setFavoriteMovie(movie)
        }
    }


}