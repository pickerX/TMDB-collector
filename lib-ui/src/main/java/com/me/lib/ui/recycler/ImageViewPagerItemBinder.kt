package com.me.lib.ui.recycler

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.me.lib.ui.AdapterBindCallback
import com.me.lib.ui.GenericListAdapter
import com.me.lib.ui.OnItemClickListener
import com.me.lib.ui.R
import kotlinx.android.synthetic.main.view_simple_view_pager.view.*

/**
 * 图片类型的ViewPager
 */
open class ImageViewPagerItemBinder<T> : ItemViewCreator<T>, RecyclerDataManager<T> {

    override fun layout(): Int = R.layout.view_simple_view_pager

    override fun getItemViewType(): Int = hashCode()
    private var adapter: GenericListAdapter<T>? = null

    // for override
    var onItemClickListener: OnItemClickListener? = null
    var onViewBinder: AdapterBindCallback<T>? = null

    override val data: MutableList<T> = mutableListOf()

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

    override fun getItem(position: Int): T = get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun newViewHolder(itemView: View): ViewHolder<*> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val vp2 = holder.itemView.view_pager2
        adapter = GenericListAdapter(data, itemViewFactory = {
            val imageView = ImageView(holder.itemView.context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView
        })
            .also {
                it.onItemClickListener = onItemClickListener
                it.onViewBinder = onViewBinder
            }
        vp2.adapter = adapter
    }
}