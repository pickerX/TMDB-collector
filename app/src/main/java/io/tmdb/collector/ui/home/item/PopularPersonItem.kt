package io.tmdb.collector.ui.home.item

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.me.lib.ui.OnItemClickListener
import com.me.lib.ui.OnVHBinding
import com.me.lib.ui.recycler.ViewHolder
import com.me.lib.ui.recycler.adapter.GenericAdapter
import com.me.lib.ui.recycler.item.DefaultItem
import io.tmdb.collector.R
import io.tmdb.collector.data.Person
import io.tmdb.collector.databinding.ItemPopularPersonBinding
import io.tmdb.collector.databinding.ViewRecyclerViewBinding

/**
 *
 * @author: pickerx
 * @date:2021/2/26 5:18 PM
 */
class PopularPersonItem : DefaultItem<Person>() {

    override fun layout(): Int = R.layout.view_recycler_view

    override fun newViewHolder(itemView: View): ViewHolder<*> =
        PopularPersonVM(itemView, adapter, onItemClickListener)

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        adapter.updateAll(dataSet)
    }

    private val onVHBinding = object : OnVHBinding<Person, ItemPopularPersonBinding> {
        override fun invoke(
            binding: ItemPopularPersonBinding,
            dataSet: MutableList<Person>,
            position: Int
        ) {
            val person = dataSet[position]
            binding.ivIcon.load(person.profile_path) {
                error(R.mipmap.ic_launcher)
                placeholder(R.mipmap.ic_launcher)
            }
            binding.tvTitle.text = person.name
        }
    }

    private val adapter =
        GenericAdapter<Person, ItemPopularPersonBinding>(R.layout.item_popular_person)
            .also {
                it.onVHBinding = onVHBinding
            }

    class PopularPersonVM(
        itemView: View,
        adapter: GenericAdapter<Person, ItemPopularPersonBinding>,
        onItemClickListener: OnItemClickListener?
    ) : ViewHolder<ViewRecyclerViewBinding>(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(it, adapterPosition)
            }

//            binding.recyclerView.background = ResourcesCompat.getDrawable(
//                itemView.context.resources,
//                R.drawable.shape_top_radius,
//                null
//            )
            binding.recyclerView.let {
                it.adapter = adapter
                it.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

}