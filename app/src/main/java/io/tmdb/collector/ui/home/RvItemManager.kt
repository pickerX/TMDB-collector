package io.tmdb.collector.ui.home

import com.me.lib.ui.recycler.adapter.MuxAdapter
import io.tmdb.collector.data.Movie
import io.tmdb.collector.data.Person
import io.tmdb.collector.ui.home.item.PopularPersonItem
import io.tmdb.collector.ui.home.item.TagRecyclerItems
import io.tmdb.collector.ui.home.item.ViewPagerItem

/**
 *
 * @author: pickerx
 * @date:2021/2/26 5:21 PM
 */
class RvItemManager(private val adapter: MuxAdapter) {

    private lateinit var viewPagerItem: ViewPagerItem
    private lateinit var popularPersonItem: PopularPersonItem
    private lateinit var tagRecyclerItems: TagRecyclerItems

    fun load() {
        tagRecyclerItems = TagRecyclerItems()
        viewPagerItem = ViewPagerItem()
        popularPersonItem = PopularPersonItem()


        adapter.apply {
            addItem(viewPagerItem)
            addItem(popularPersonItem)
            addItem(tagRecyclerItems.upComingItem)
            addItem(tagRecyclerItems.topRatedItem)
        }
    }

    fun bindPopularMovies(results: List<Movie>) {
        viewPagerItem.update(results)
        adapter.notifyItemChanged(0)
    }

    fun bindPopularPerson(results: List<Person>) {
        popularPersonItem.update(results)
        adapter.notifyItemChanged(1)
    }

    fun bindUpcomingMovies(results: List<Movie>) {
        tagRecyclerItems.upComingItem.updateAll(results)
        adapter.notifyItemChanged(2)
    }

    fun bindTopRatedMovies(results: List<Movie>) {
        tagRecyclerItems.topRatedItem.updateAll(results)
        adapter.notifyItemChanged(3)
    }
}