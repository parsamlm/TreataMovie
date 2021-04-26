package ir.pmoslem.treatamovie.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.databinding.DetailsFragmentBinding
import ir.pmoslem.treatamovie.viewmodel.DetailsViewModel

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    private lateinit var viewModel: DetailsViewModel
    private lateinit var viewBinding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        viewBinding = DetailsFragmentBinding.inflate(inflater, container, false)

        val root = viewBinding.root

        val movie = arguments?.let { DetailsFragmentArgs.fromBundle(it).movie }
        viewBinding.detailsToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )


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
                    viewBinding.webViewDetails.loadData(movieDetails.summary, "text/html", "utf-8")
                }
            })




        return root
    }


}