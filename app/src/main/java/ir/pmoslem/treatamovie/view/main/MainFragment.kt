package ir.pmoslem.treatamovie.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.R
import ir.pmoslem.treatamovie.databinding.FragmentMainBinding
import ir.pmoslem.treatamovie.view.contentfavorite.ContentFavoritePagerAdapter

private const val CURRENT_PAGE_NUMBER = 0

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var viewBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        val root = viewBinding.root

        val contentFavoritePagerAdapter = ContentFavoritePagerAdapter(requireActivity())

        viewBinding.viewPager2Main.adapter = contentFavoritePagerAdapter
        viewBinding.viewPager2Main.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewBinding.viewPager2Main.currentItem = CURRENT_PAGE_NUMBER

        val tabLayoutMediator = TabLayoutMediator(viewBinding.tabsMain, viewBinding.viewPager2Main) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.tab_text_1)
                1 -> tab.setText(R.string.tab_text_2)
            }
        }
        tabLayoutMediator.attach()

        return root
    }

}