package ir.pmoslem.treatamovie.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.databinding.FragmentMainBinding
import ir.pmoslem.treatamovie.model.Movie
import ir.pmoslem.treatamovie.viewmodel.PageViewModel

@AndroidEntryPoint
class PlaceholderFragment : Fragment(), ItemChangeListener {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewBinding: FragmentMainBinding

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        val root = viewBinding.root
        moviesAdapter = MoviesAdapter(root.context)

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

        pageViewModel.getPageNumber().observe(requireActivity(), Observer { num ->
            if (num == 1) {
                pageViewModel.getFavoriteContentListFromDatabase().observe(requireActivity(),
                        { moviesList ->
                            if (moviesList != null) {
                                moviesAdapter.setMovies(this, moviesList)
                            }
                            viewBinding.rvMain.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
                            viewBinding.rvMain.adapter = moviesAdapter
                        })

            }else{
                pageViewModel.getContentListFromDatabase().observe(requireActivity(),
                        { moviesList ->
                            if (moviesList != null) {
                                moviesAdapter.setMovies(this, moviesList)
                            }
                            viewBinding.rvMain.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
                            viewBinding.rvMain.adapter = moviesAdapter
                        })
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

    override fun onDetailButtonClicked(movie: Movie) {

    }
}