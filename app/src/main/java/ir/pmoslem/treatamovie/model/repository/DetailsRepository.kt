package ir.pmoslem.treatamovie.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.db.MovieDetails
import ir.pmoslem.treatamovie.model.db.MovieDetailsDao
import ir.pmoslem.treatamovie.model.server.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
private const val TAG = "DetailsRepository"

class DetailsRepository @Inject constructor(
    private val api: ApiService,
    private val movieDao: MovieDao,
    private val movieDetailsDao: MovieDetailsDao
) {

    init {
        progressBarStatus.postValue(true)
    }

    fun getMovieDetailsFromServer(requestId: Long): LiveData<MovieDetails> {
        val movieDetailsLiveData = MutableLiveData<MovieDetails>()
        val innerJson = JsonObject()
        innerJson.addProperty("RequestID", requestId)

        val requestBody = JsonObject()
        requestBody.add("request", innerJson)

        try {
            GlobalScope.launch(Dispatchers.IO) {
                val movieDetailsObject = api.getContentDetail(requestBody)
                val movieDetails = movieDetailsObject.movieDetails
                movieDetailsDao.insertMovieDetailsContent(movieDetails)
                movieDetailsLiveData.postValue(movieDetails)
                progressBarStatus.postValue(false)
            }

        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } catch (e: HttpException) {
            Log.e(TAG, e.toString())
        }
        return movieDetailsLiveData
    }

    fun getProgressBarStatus(): LiveData<Boolean> = progressBarStatus


}