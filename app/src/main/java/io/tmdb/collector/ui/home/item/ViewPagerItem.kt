package io.tmdb.collector.ui.home.item

import android.view.View
import coil.load
import com.me.lib.ui.recycler.DefaultItem
import com.me.lib.ui.recycler.ViewHolder
import io.tmdb.collector.R
import io.tmdb.collector.api.Api
import io.tmdb.collector.data.Movie
import io.tmdb.collector.databinding.ItemMoviePosterBinding

/**
 * 海报 item 项
 * @author: pickerx
 * @date:2020/11/16 4:34 PM
 */
class ViewPagerItem : DefaultItem<Movie>() {

    override fun layout(): Int = R.layout.item_view_pager2

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val data = getItem(position)
        val poster = holder as PosterVM
        with(poster.binding) {
            data.poster_path?.let { ivPoster.load(Api.getPosterPath(it)) }

            tvTitle.text = data.original_title
        }

    }

    override fun newViewHolder(itemView: View): ViewHolder<*> {
        return PosterVM(itemView)
    }

    class PosterVM(itemView: View) : ViewHolder<ItemMoviePosterBinding>(itemView)

}