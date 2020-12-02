package com.me.lib.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Space for RecycleView GridLayout
 * count, Horizontal spacing, Vertical spacing
 */
class GridSpacingItemDecoration(
    private var spanCount: Int,
    private var spacingHorizontal: Int = 10,
    private var spacingVertical: Int = 15,
    private var includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // item position
        val position = parent.getChildAdapterPosition(view)
        // item column
        val column = position % spanCount
        if (includeEdge) {
            // spacing - column * ((1f / spanCount) * spacing)
            outRect.left =
                spacingHorizontal - column * spacingHorizontal / spanCount
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacingHorizontal / spanCount
            // top edge
            if (position < spanCount) {
                outRect.top = spacingVertical
            }
            // item bottom
            outRect.bottom = spacingVertical
        } else {
            // column * ((1f / spanCount) * spacing)
            outRect.left = column * spacingHorizontal / spanCount
            // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            outRect.right =
                spacingHorizontal - (column + 1) * spacingHorizontal / spanCount
            if (position >= spanCount) {
                // item top
                outRect.top = spacingVertical
            }
        }
    }
}