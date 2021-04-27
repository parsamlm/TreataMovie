package ir.pmoslem.treatamovie.view.contentfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.databinding.FragmentContentFavoriteBinding
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.view.main.MainFragmentDirections
import ir.pmoslem.treatamovie.viewmodel.ContentFavoriteViewModel

private const val CONTENT_PAGE_INDEX = 0
private const val FAVORITE_PAGE_INDEX = 1

@AndroidEntryPoint
class ContentFavoriteFragment : Fragment(), ItemChangeListener {

    private lateinit var viewBinding: FragmentContentFavoriteBinding
    private lateinit var contentMoviesAdapter: ContentMoviesAdapter
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var favoriteMovieList: List<Movie>

    private val viewModel: ContentFavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply { setPageIndex(arguments?.getInt(PAGE_INDEX) ?: 1) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentContentFavoriteBinding.inflate(inflater, container, false)
        val root = viewBinding.root

        // Content & Favorite page adapter defining
        contentMoviesAdapter = ContentMoviesAdapter(this)
        favoriteMoviesAdapter = FavoriteMoviesAdapter()

        viewBinding.rvMain.layoutManager =
            LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)

        viewModel.getPageIndex().observe(viewLifecycleOwner, { num ->
            if (num == CONTENT_PAGE_INDEX) { // Observe adapter items for content page
                viewModel.getMoviesFromServer().observe(viewLifecycleOwner) {
                    contentMoviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    viewBinding.rvMain.adapter = contentMoviesAdapter
                }

            } else if (num == FAVORITE_PAGE_INDEX) { // Observe adapter items for favorite page
                viewModel.getFavoriteMoviesFromDatabase()
                    .observe(requireActivity(),
                        { moviesList ->
                            if (moviesList != null) {
                                favoriteMovieList = moviesList
                                favoriteMoviesAdapter.setMovies(this, moviesList)
                                viewBinding.rvMain.adapter = favoriteMoviesAdapter
                            }
                        })
            }
        })

        return root
    }


    companion object {
        private const val PAGE_INDEX = "page_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): ContentFavoriteFragment {
            return ContentFavoriteFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGE_INDEX, sectionNumber)
                }
            }
        }
    }


    override fun onFavoriteButtonClicked(movie: Movie) { // Favorite Button logic implementation
        viewModel.onFavoriteButtonClicked(movie)
    }

    override fun onDetailsButtonClicked(movie: Movie) { // Details Button logic implementation
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailsFragment(movie)
        )
    }

}