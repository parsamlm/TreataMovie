package ir.pmoslem.treatamovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.model.db.MovieDetails
import ir.pmoslem.treatamovie.model.repository.DetailsRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {

    fun getMovieDetailsFromServer(requestId: Long): LiveData<MovieDetails> =
        detailsRepository.getMovieDetailsFromServer(requestId)

    fun isFavoriteMovie(movie: Movie): LiveData<Boolean> =
        detailsRepository.isFavoriteMovieExistsInDatabase(movie)

    fun updateMovie(movie: Movie) = detailsRepository.updateMovie(movie)


    fun getProgressBarStatus(): LiveData<Boolean> = detailsRepository.getProgressBarStatus()

    fun isErrorOccurred(): LiveData<Boolean> = detailsRepository.isErrorOccurred()


}