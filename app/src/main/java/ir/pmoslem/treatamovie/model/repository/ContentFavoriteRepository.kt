package ir.pmoslem.treatamovie.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.server.ApiService
import ir.pmoslem.treatamovie.model.server.MovieRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContentFavoriteRepository @Inject constructor(
    private val api: ApiService,
    private val movieDao: MovieDao
) {

    fun getMoviesFromServer() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieRemoteMediator(api, movieDao) }
        ).liveData


    fun getFavoriteMoviesFromDatabase(): LiveData<List<Movie>> =
        movieDao.getFavoriteMoviesLiveData()

    fun onFavoriteButtonClicked(movie: Movie) {
        movie.favoriteStatus = !movie.favoriteStatus
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.setFavoriteMovie(movie)
        }
    }

}