package ir.pmoslem.treatamovie.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import ir.pmoslem.treatamovie.R
import ir.pmoslem.treatamovie.model.Movie

private lateinit var contentItemChangeListener: ItemChangeListener

class ContentMoviesAdapter constructor(itemChangeListener: ItemChangeListener) :
    PagingDataAdapter<Movie, ContentMoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    init {
        contentItemChangeListener = itemChangeListener
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.contentId == newItem.contentId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbIv: ImageView = itemView.findViewById(R.id.iv_thumbPic_item)
        private val title: TextView = itemView.findViewById(R.id.tv_title_item)
        private val type: TextView = itemView.findViewById(R.id.tv_type_item)
        private val favoriteIv: ImageView = itemView.findViewById(R.id.animation_favorite_item)
        private val moreDetailBtn: MaterialButton = itemView.findViewById(R.id.btn_moreDetails_item)

        fun bind(movie: Movie) {
            title.text = movie.title
            type.text = if (movie.zoneId == 3) "نوع فیلم: سریال" else "نوع فیلم: سینمایی"
            thumbIv.load(movie.thumbImgLink)
            if (movie.favoriteStatus) favoriteIv.setImageResource(R.drawable.ic_round_favorite_24)
            else favoriteIv.setImageResource(R.drawable.ic_round_favorite_border_24)
            favoriteIv.setOnClickListener {
                contentItemChangeListener.onFavoriteButtonClicked(movie)
            }

            moreDetailBtn.setOnClickListener {
                contentItemChangeListener.onDetailButtonClicked(movie)
            }

        }

    }


}

interface ItemChangeListener {
    fun onFavoriteButtonClicked(movie: Movie)
    fun onDetailButtonClicked(movie: Movie)
}