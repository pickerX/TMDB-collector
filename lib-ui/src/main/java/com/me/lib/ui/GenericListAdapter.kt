package com.me.lib.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/** Type helper used for the callback triggered once our view has been bound */
typealias AdapterBindCallback<T> = (view: View, data: T, position: Int) -> Unit

/** Type helper used for the callback triggered once our view has been bound */
typealias OnItemClickListener = (view: View, position: Int) -> Unit

/** List adapter for generic types, intended used for small-medium lists of data */
class GenericListAdapter<T>(
    private var dataset: List<T>,
    private val itemLayoutId: Int? = null,
    private val itemViewFactory: (() -> View)? = null
) : RecyclerView.Adapter<GenericListAdapter.GenericListViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var onViewBinder: AdapterBindCallback<T>? = null

    class GenericListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericListViewHolder {
        val holder = GenericListViewHolder(
            when {
                itemViewFactory != null -> itemViewFactory.invoke()
                itemLayoutId != null -> {
                    LayoutInflater.from(parent.context)
                        .inflate(itemLayoutId, parent, false)
                }
                else -> {
                    throw IllegalStateException(
                        "Either the layout ID or the view factory need to be non-null"
                    )
                }
            }
        )
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(it, holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: GenericListViewHolder, position: Int) {
        if (position < 0 || position > dataset.size) return
        onViewBinder?.invoke(holder.view, dataset[position], position)
    }

    override fun getItemCount() = dataset.size

    fun setData(data: List<T>) {
        dataset = data
        notifyDataSetChanged()
    }
}