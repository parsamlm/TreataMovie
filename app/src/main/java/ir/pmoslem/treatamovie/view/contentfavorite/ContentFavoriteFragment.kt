package ir.pmoslem.treatamovie.view.contentfavorite

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.R
import ir.pmoslem.treatamovie.databinding.FragmentContentFavoriteBinding
import ir.pmoslem.treatamovie.model.db.Movie
import ir.pmoslem.treatamovie.view.details.DetailsFragment
import ir.pmoslem.treatamovie.view.details.DetailsFragmentDirections
import ir.pmoslem.treatamovie.view.main.MainFragmentDirections
import ir.pmoslem.treatamovie.viewmodel.PageViewModel

private const val FAVORITE_PAGE_INDEX = 1

@AndroidEntryPoint
class PlaceholderFragment : Fragment(), ItemChangeListener {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewBinding: FragmentContentFavoriteBinding

    private lateinit var contentMoviesAdapter: ContentMoviesAdapter
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var favoriteMovieList: List<Movie>
    private lateinit var fragmentTransaction: FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentContentFavoriteBinding.inflate(inflater, container, false)

        fragmentTransaction = childFragmentManager.beginTransaction()

        val root = viewBinding.root
        contentMoviesAdapter = ContentMoviesAdapter(this)
        favoriteMoviesAdapter = FavoriteMoviesAdapter()

        viewBinding.rvMain.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)



        pageViewModel.getProgressBarStatus().observe(requireActivity(),
            { visibility ->
                if(visibility!!){
                    viewBinding.pbLoadMain.visibility = View.VISIBLE
                    viewBinding.rvMain.visibility = View.GONE
                }else{
                    viewBinding.pbLoadMain.visibility = View.GONE
                    viewBinding.rvMain.visibility = View.VISIBLE
                }
            })

        pageViewModel.getPageNumber().observe(viewLifecycleOwner, { num ->
            if (num == FAVORITE_PAGE_INDEX) {
                pageViewModel.getFavoriteContentListFromDatabase().observe(requireActivity(),
                        { moviesList ->
                            if (moviesList != null) {
                                favoriteMovieList = moviesList
                                favoriteMoviesAdapter.setMovies(this, moviesList)
                                viewBinding.rvMain.adapter = favoriteMoviesAdapter
                            }
                        })

            }else{
                pageViewModel.getContentListFromServer().observe(viewLifecycleOwner){
                    it.map { movie -> if(favoriteMovieList[favoriteMovieList.indexOf(movie)].favoriteStatus) movie.favoriteStatus = true  }
                    contentMoviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    viewBinding.rvMain.adapter = contentMoviesAdapter
                }
            }
        })





        return root
    }


    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onFavoriteButtonClicked(movie: Movie) {
        pageViewModel.onFavoriteButtonClicked(movie)


    }

    override fun onDetailsButtonClicked(movie: Movie) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(movie))
    }

}