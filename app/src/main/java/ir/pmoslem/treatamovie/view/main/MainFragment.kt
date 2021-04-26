package ir.pmoslem.treatamovie.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.treatamovie.R
import ir.pmoslem.treatamovie.view.contentfavorite.SectionsPagerAdapter

@AndroidEntryPoint
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager2: ViewPager2 = root.findViewById(R.id.view_pager)
        viewPager2.adapter = sectionsPagerAdapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.currentItem = 0
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        val tabLayoutMediator = TabLayoutMediator(tabs, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.tab_text_1, )
                1 -> tab.setText(R.string.tab_text_2)
            }
        }
        tabLayoutMediator.attach()

        return root
    }


}