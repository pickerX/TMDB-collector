package com.me.lib.ui.layout

import android.content.Context
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout

/**
 *
 * @author: pickerx
 * @date:2021/3/10 3:14 PM
 */
class FitConstraintLayout(
    @NonNull context: Context,
    @Nullable attrs: AttributeSet,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0)

    override fun fitSystemWindows(insets: Rect?): Boolean {
        insets?.top = 0
        return super.fitSystemWindows(insets)
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val b = Insets.of(
                0, -insets!!.systemWindowInsets.top, 0, 0
            )
            // 使两个inset.top字段相消为0
            val result: Insets = Insets.add(insets.systemWindowInsets, b)
            val builder = WindowInsets.Builder(insets).setSystemWindowInsets(result)
            return super.dispatchApplyWindowInsets(builder.build())
        }
        return super.dispatchApplyWindowInsets(insets)
    }
}