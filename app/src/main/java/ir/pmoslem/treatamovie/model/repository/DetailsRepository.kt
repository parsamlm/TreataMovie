package ir.pmoslem.treatamovie.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.server.ApiService
import javax.inject.Inject

private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
class DetailsRepository @Inject constructor(private val api: ApiService, private val movieDao: MovieDao, ) {

    init {
        progressBarStatus.postValue(true)
    }

    fun getMovieDetailsFromServer(): LiveData<Movie>{
        //todo continue from here
    }
}