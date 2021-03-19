package com.me.lib.ui.recycler.item

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.lib.ui.*
import com.me.lib.ui.databinding.ViewSimpleListBinding
import com.me.lib.ui.recycler.ItemViewCreator
import com.me.lib.ui.recycler.RecyclerDataManager
import com.me.lib.ui.recycler.ViewHolder
import com.me.lib.ui.recycler.adapter.GenericAdapter

/**
 * T: data type
 * E: ViewDataBinding type
 */
open class SimpleRecyclerViewItem<T, Binding : ViewDataBinding>(
    /** recyclerview item layout id*/
    itemLayoutId: Int,
    private val titleTag: String = "",
    private val orientation: Int = RecyclerView.VERTICAL
) :
    ItemViewCreator<T>, RecyclerDataManager<T> {

    /** Item recyclerView's child onBindViewHolder callback */
    open var onViewBinder: OnVHBinding<T, Binding>? = null
        set(value) {
            if (value == onViewBinder) return
            field = value
            //adapter.onVHBinding = field
        }

    override val dataSet = mutableListOf<T>()

    override var onItemClickListener: OnItemClickListener? = null

    override fun layout(): Int = R.layout.view_simple_list

    override fun getItemViewType(): Int = hashCode()

    override fun getItem(position: Int): T = dataSet[position]

    override fun getItemId(position: Int): Long = adapter.getItemId(position)

    override fun newViewHolder(itemView: View): ViewHolder<*> =
        SimpleRecyclerItemVH(itemView, adapter, orientation, titleTag)

    private val adapter = GenericAdapter<T, Binding>(itemLayoutId)
        .also {
            it.onVHBinding = onViewBinder
            it.onItemClickListener = onItemClickListener
        }

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        adapter.updateAll(dataSet)
        Log.e(
            "SimpleViewItem",
            ">>>adapter ${adapter.onVHBinding == null}  >> ${onViewBinder == null}"
        )
    }

    override fun notifyDataSetChanged() {

    }
}

class SimpleRecyclerItemVH<T, Binding : ViewDataBinding>(
    itemView: View,
    adapter: GenericAdapter<T, Binding>,
    orientation: Int,
    titleTag: String = ""
) :
    ViewHolder<ViewSimpleListBinding>(itemView) {
    init {
        if (titleTag.isNotEmpty()) {
            binding.tvTitleTag.visible()
            binding.tvTitleTag.text = titleTag
        } else binding.tvTitleTag.gone()

        binding.recycleView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(itemView.context, orientation, false)
//            if (orientation == RecyclerView.HORIZONTAL) {
//                it.setBackgroundColor(itemView.context.resources.getColor(R.color.app_bg))
//            } else {
//                it.addItemDecoration(DividerItemDecoration(itemView.context, LinearLayout.VERTICAL))
//                val margin = (it.layoutParams as ViewGroup.MarginLayoutParams)
//                // margin.setMargins(left, top, right, bottom)
//                it.layoutParams = margin
//            }
        }
    }
}
