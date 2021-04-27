package ir.pmoslem.treatamovie.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.R
import ir.pmoslem.treatamovie.databinding.DetailsFragmentBinding
import ir.pmoslem.treatamovie.view.showBackButtonInToolbar
import ir.pmoslem.treatamovie.view.showSnackBar
import ir.pmoslem.treatamovie.viewmodel.DetailsViewModel

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var viewBinding: DetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)

        val root = viewBinding.root

        val movie = arguments?.let { DetailsFragmentArgs.fromBundle(it).movie }

        if (movie != null){
            viewModel.isFavoriteMovie(movie).observe(viewLifecycleOwner,
                { isFavoriteMovie ->
                    viewBinding.ivFavoriteDetails.setImageResource(
                        if (isFavoriteMovie == true) R.drawable.ic_round_favorite_24
                        else R.drawable.ic_round_favorite_border_24
                    )
                })
        }

        viewBinding.mcvFavoriteDetails.setOnClickListener {
            if (movie != null)
                viewModel.updateMovie(movie)
        }


        viewModel.isErrorOccurred().observe(viewLifecycleOwner,
            { isOccurred ->
                if (isOccurred == true){
                    showSnackBar("No internet connection", R.color.orange_500)
                }
            })


        viewModel.getProgressBarStatus().observe(viewLifecycleOwner,
            { visibility ->
                if (visibility == true) {
                    viewBinding.pbLoadDetails.visibility = View.VISIBLE
                    viewBinding.appbarDetails.visibility = View.GONE
                    viewBinding.linearLayoutDetails.visibility = View.GONE
                } else {
                    viewBinding.pbLoadDetails.visibility = View.GONE
                    viewBinding.appbarDetails.visibility = View.VISIBLE
                    viewBinding.linearLayoutDetails.visibility = View.VISIBLE
                }
            })

        viewModel.getMovieDetailsFromServer(movie!!.contentId).observe(viewLifecycleOwner,
            { movieDetails ->
                if (movieDetails != null) {
                    viewBinding.ivLandscapeDetails.load(movieDetails.landscapeImageUrl)
                    viewBinding.detailsToolbar.title = movieDetails.title
                    viewBinding.webViewDetails.loadData(
                        movieDetails.summary,
                        "text/html",
                        "utf-8"
                    )
                }
            })

        showBackButtonInToolbar(
            toolbar = viewBinding.toolbarDetails
        )

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}