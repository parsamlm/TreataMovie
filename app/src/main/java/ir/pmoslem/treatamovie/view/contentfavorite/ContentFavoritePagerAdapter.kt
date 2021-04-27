package ir.pmoslem.treatamovie.view.contentfavorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val CONTENT_FAVORITE_PAGE_COUNT = 2

class ContentFavoritePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return CONTENT_FAVORITE_PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return ContentFavoriteFragment.newInstance(position)
    }


}