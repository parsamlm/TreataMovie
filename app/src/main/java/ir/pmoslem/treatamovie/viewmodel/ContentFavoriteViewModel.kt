package ir.pmoslem.treatamovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.repository.ContentFavoriteRepository
import javax.inject.Inject

@HiltViewModel
class ContentFavoriteViewModel @Inject constructor(
    private val contentFavoriteRepository: ContentFavoriteRepository
    ) : ViewModel() {

    private val pageIndex = MutableLiveData<Int>()

    fun getMoviesFromServer(): LiveData<PagingData<Movie>> {
        return contentFavoriteRepository.getMoviesFromServer().cachedIn(viewModelScope)
    }

    fun getFavoriteMoviesFromDatabase(): LiveData<List<Movie>> =
        contentFavoriteRepository.getFavoriteMoviesFromDatabase()

    fun onFavoriteButtonClicked(movie: Movie) = contentFavoriteRepository.onFavoriteButtonClicked(movie)

    fun setPageIndex(index: Int) {
        pageIndex.value = index
    }

    fun getPageIndex(): LiveData<Int> = pageIndex
}