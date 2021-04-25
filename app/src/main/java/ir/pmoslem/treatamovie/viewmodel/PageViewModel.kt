package ir.pmoslem.treatamovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.treatamovie.model.Movie
import ir.pmoslem.treatamovie.model.repository.ContentRepository
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class PageViewModel @Inject constructor(private val contentRepository: ContentRepository)  : ViewModel() {

    private val _index = MutableLiveData<Int>()

    init {
        contentRepository.getContentListFromServer()
    }

    fun getContentListFromDatabase():LiveData<List<Movie>> = contentRepository.getContentListFromDatabase()

    fun getFavoriteContentListFromDatabase():LiveData<List<Movie>> = contentRepository.getFavoriteContentListFromDatabase()

    fun getProgressBarStatus():LiveData<Boolean> = contentRepository.getProgressBarStatus()

    fun onFavoriteButtonClicked(movie: Movie) = contentRepository.onFavoriteButtonClicked(movie)

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getPageNumber(): LiveData<Int> = _index
}