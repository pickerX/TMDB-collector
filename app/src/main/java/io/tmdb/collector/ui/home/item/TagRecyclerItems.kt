package io.tmdb.collector.ui.home.item

import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.me.lib.ui.OnVHBinding
import com.me.lib.ui.recycler.item.SimpleRecyclerViewItem
import io.tmdb.collector.R
import io.tmdb.collector.data.Movie
import io.tmdb.collector.databinding.ItemMovieCardBinding
import timber.log.Timber

/**
 * common item for movies tag
 *
 * @author: pickerx
 * @date:2021/3/5 9:38 AM
 */
class TagRecyclerItems {
    private val onViewBinder = object : OnVHBinding<Movie, ItemMovieCardBinding> {
        override fun invoke(
            binding: ItemMovieCardBinding,
            dataSet: MutableList<Movie>,
            position: Int
        ) {
            val movie = dataSet[position]
            Timber.e("onViewBinder>>>$movie")
            binding.ivPoster.load(movie.poster_path)
            binding.tvTitle.text = movie.title
            binding.tvSummary.text = "${movie.vote_average}"
        }

    }

    val upComingItem = SimpleRecyclerViewItem<Movie, ItemMovieCardBinding>(
        R.layout.item_movie_card,
        "Coming soon",
        LinearLayoutManager.HORIZONTAL
    ).also {
        it.onViewBinder = onViewBinder
    }

    val topRatedItem = SimpleRecyclerViewItem<Movie, ItemMovieCardBinding>(
        R.layout.item_movie_card,
        "Coming soon",
        LinearLayoutManager.HORIZONTAL
    ).also {
        it.onViewBinder = onViewBinder
    }
}
