package io.tmdb.collector.ui.home.item

import android.view.View
import coil.load
import com.me.lib.ui.OnVHBinding
import com.me.lib.ui.recycler.ViewHolder
import com.me.lib.ui.recycler.adapter.GenericAdapter
import com.me.lib.ui.recycler.item.DefaultItem
import io.tmdb.collector.R
import io.tmdb.collector.data.Movie
import io.tmdb.collector.databinding.ItemMoviePosterBinding
import io.tmdb.collector.databinding.ItemViewPager2Binding
import timber.log.Timber

/**
 * 海报 item 项
 * @author: pickerx
 * @date:2020/11/16 4:34 PM
 */
class ViewPagerItem : DefaultItem<Movie>() {

    override fun layout(): Int = R.layout.item_view_pager2

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        adapter.updateAll(dataSet)
    }

    private val adapter = GenericAdapter<Movie, ItemMoviePosterBinding>(R.layout.item_movie_poster)
        .also {
            it.onItemClickListener = onItemClickListener

            it.onVHBinding = object : OnVHBinding<Movie, ItemMoviePosterBinding> {
                override fun invoke(
                    binding: ItemMoviePosterBinding,
                    dataSet: MutableList<Movie>,
                    position: Int
                ) {
                    val d = dataSet[position]
                    binding.ivPoster.load(d.backdrop_path) {
                        placeholder(R.mipmap.dark)
                    }

                    binding.tvTitle.text = d.original_title
                    binding.tvTags.text = d.keywords?.get(0)?.name
                }
            }
        }

    override fun newViewHolder(itemView: View): ViewHolder<*> {
        return PosterVM(itemView, adapter)
    }

    class PosterVM(itemView: View, adapter: GenericAdapter<Movie, ItemMoviePosterBinding>) :
        ViewHolder<ItemViewPager2Binding>(itemView) {
        init {
            binding.viewPager2.adapter = adapter
        }
    }

}