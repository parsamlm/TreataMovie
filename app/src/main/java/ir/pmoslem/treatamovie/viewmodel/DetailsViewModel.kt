package ir.pmoslem.treatamovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.treatamovie.model.db.MovieDetails
import ir.pmoslem.treatamovie.model.repository.DetailsRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {


    fun getMovieDetailsFromServer(requestId: Long): LiveData<MovieDetails> {
        return detailsRepository.getMovieDetailsFromServer(requestId)
    }


    fun getProgressBarStatus(): LiveData<Boolean> = detailsRepository.getProgressBarStatus()


}