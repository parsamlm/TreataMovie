package ir.pmoslem.treatamovie.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import ir.pmoslem.treatamovie.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

class ContentRepository @Inject constructor(private val api: ApiService, private val movieDao: MovieDao){

    init {
        progressBarStatus.postValue(true)
    }

    fun getContentListFromServer() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {MoviePagingSource(api, movieDao)}
        ).liveData.also { progressBarStatus.postValue(false) }


    fun getFavoriteContentListFromDatabase(): LiveData<List<Movie>> = movieDao.getFavoriteMovies()

    fun getProgressBarStatus(): LiveData<Boolean> = progressBarStatus

    fun onFavoriteButtonClicked(movie: Movie) {
        movie.favoriteStatus = !movie.favoriteStatus
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.setFavoriteMovie(movie)
        }
    }






}