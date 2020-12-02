package com.me.lib.ui.recycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.lib.ui.AdapterBindCallback
import com.me.lib.ui.GenericListAdapter
import com.me.lib.ui.OnItemClickListener
import com.me.lib.ui.R
import kotlinx.android.synthetic.main.view_simple_list.view.*

open class SimpleRecyclerItemBinder<T>(
    private val context: Context,
    private val layoutId: Int,
    private val orientation: Int = RecyclerView.VERTICAL
) :
    ItemViewCreator<T>, RecyclerDataManager<T> {

    override val data = mutableListOf<T>()

    var onViewBinder: AdapterBindCallback<T>? = null

    var onItemClickListener: OnItemClickListener? = null

    private var adapter: GenericListAdapter<T>? = null

    override fun layout(): Int = R.layout.view_simple_list

    override fun getItemViewType(): Int = hashCode()

    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0

    fun setMargin(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

    override fun getItem(position: Int): T = data[position]

    override fun getItemId(position: Int): Long = adapter?.getItemId(position) ?: 0

    override fun newViewHolder(itemView: View): ViewHolder<*> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val recyclerView = holder.itemView.recycle_view
        adapter = GenericListAdapter(data, layoutId).also {
            it.onViewBinder = onViewBinder
            it.onItemClickListener = onItemClickListener
        }
        recyclerView.layoutManager =
            LinearLayoutManager(context, orientation, false)
        recyclerView.adapter = adapter

        if (orientation == RecyclerView.HORIZONTAL) {
            recyclerView.setBackgroundColor(context.resources.getColor(R.color.app_bg))
        } else {
            recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            val margin = (recyclerView.layoutParams as ViewGroup.MarginLayoutParams)
            margin.setMargins(left, top, right, bottom)
            recyclerView.layoutParams = margin
        }
    }

}
