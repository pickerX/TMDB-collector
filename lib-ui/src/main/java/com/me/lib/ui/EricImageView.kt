package com.me.lib.ui


import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min


class EricImageView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    // 是否显示为圆形，如果为圆形则设置的corner无效
    private var isCircle = false

    // border、inner_border是否覆盖图片
    private var isCoverSrc = false

    // 边框宽度
    private var borderWidth = 0
    private var borderColor = Color.WHITE // 边框颜色

    // 内层边框宽度
    private var innerBorderWidth = 0

    // 内层边框充色
    private var innerBorderColor = Color.WHITE

    // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private var cornerRadius = 0

    // 左上角圆角半径
    private var cornerTopLeftRadius = 0

    // 右上角圆角半径
    private var cornerTopRightRadius = 0

    // 左下角圆角半径
    private var cornerBottomLeftRadius = 0

    // 右下角圆角半径
    private var cornerBottomRightRadius = 0

    // 遮罩颜色
    private var maskColor = 0
    private var xfermode: Xfermode? = null
    private var mWidth = 0
    private var mHeight = 0
    private var radius = 0f
    private val borderRadii: FloatArray
    private val srcRadii: FloatArray

    // 图片占的矩形区域
    private var srcRectF: RectF

    // 边框的矩形区域
    private val borderRectF: RectF
    private val mPaint: Paint

    // 用来裁剪图片的ptah
    private val mPath: Path

    // 图片区域大小的path
    private var srcPath: Path? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.EricImageView, 0, 0)
        for (i in 0 until ta.indexCount) {
            val attr = ta.getIndex(i)
            when (attr) {
                R.styleable.EricImageView_is_cover_src -> {
                    isCoverSrc = ta.getBoolean(attr, isCoverSrc)
                }
                R.styleable.EricImageView_is_circle -> {
                    isCircle = ta.getBoolean(attr, isCircle)
                }
                R.styleable.EricImageView_border_width -> {
                    borderWidth = ta.getDimensionPixelSize(attr, borderWidth)
                }
                R.styleable.EricImageView_border_color -> {
                    borderColor = ta.getColor(attr, borderColor)
                }
                R.styleable.EricImageView_inner_border_width -> {
                    innerBorderWidth = ta.getDimensionPixelSize(attr, innerBorderWidth)
                }
                R.styleable.EricImageView_inner_border_color -> {
                    innerBorderColor = ta.getColor(attr, innerBorderColor)
                }
                R.styleable.EricImageView_corner_radius -> {
                    cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius)
                }
                R.styleable.EricImageView_corner_top_left_radius -> {
                    cornerTopLeftRadius = ta.getDimensionPixelSize(attr, cornerTopLeftRadius)
                }
                R.styleable.EricImageView_corner_top_right_radius -> {
                    cornerTopRightRadius = ta.getDimensionPixelSize(attr, cornerTopRightRadius)
                }
                R.styleable.EricImageView_corner_bottom_left_radius -> {
                    cornerBottomLeftRadius = ta.getDimensionPixelSize(attr, cornerBottomLeftRadius)
                }
                R.styleable.EricImageView_corner_bottom_right_radius -> {
                    cornerBottomRightRadius =
                        ta.getDimensionPixelSize(attr, cornerBottomRightRadius)
                }
                R.styleable.EricImageView_mask_color -> {
                    maskColor = ta.getColor(attr, maskColor)
                }
            }
        }
        ta.recycle()
        borderRadii = FloatArray(8)
        srcRadii = FloatArray(8)
        borderRectF = RectF()
        srcRectF = RectF()
        mPaint = Paint()
        mPath = Path()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        } else {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            srcPath = Path()
        }
        calculateRadii()
        clearInnerBorderWidth()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        initBorderRectF()
        initSrcRectF()
    }

    override fun onDraw(canvas: Canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG)
        if (!isCoverSrc) {
            val sx = 1.0f * (mWidth - 2 * borderWidth - 2 * innerBorderWidth) / mWidth
            val sy = 1.0f * (mHeight - 2 * borderWidth - 2 * innerBorderWidth) / mHeight
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, mWidth / 2.0f, mHeight / 2.0f)
        }
        super.onDraw(canvas)
        mPaint.reset()
        mPath.reset()
        if (isCircle) {
            mPath.addCircle(mWidth / 2.0f, mHeight / 2.0f, radius, Path.Direction.CCW)
        } else {
            mPath.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW)
        }
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.xfermode = xfermode
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            canvas.drawPath(mPath, mPaint)
        } else {
            srcPath!!.addRect(srcRectF, Path.Direction.CCW)
            // 计算tempPath和path的差集
            srcPath!!.op(mPath, Path.Op.DIFFERENCE)
            canvas.drawPath(srcPath!!, mPaint)
        }
        mPaint.xfermode = null

        // 绘制遮罩
        if (maskColor != 0) {
            mPaint.color = maskColor
            canvas.drawPath(mPath, mPaint)
        }
        // 恢复画布
        canvas.restore()
        // 绘制边框
        drawBorders(canvas)
    }

    private fun drawBorders(canvas: Canvas) {
        if (isCircle) {
            if (borderWidth > 0) {
                drawCircleBorder(canvas, borderWidth, borderColor, radius - borderWidth / 2.0f)
            }
            if (innerBorderWidth > 0) {
                drawCircleBorder(
                    canvas,
                    innerBorderWidth,
                    innerBorderColor,
                    radius - borderWidth - innerBorderWidth / 2.0f
                )
            }
        } else {
            if (borderWidth > 0) {
                drawRectFBorder(canvas, borderWidth, borderColor, borderRectF, borderRadii)
            }
        }
    }

    private fun drawCircleBorder(
        canvas: Canvas,
        borderWidth: Int,
        borderColor: Int,
        radius: Float
    ) {
        initBorderPaint(borderWidth, borderColor)
        mPath.addCircle(mWidth / 2.0f, mHeight / 2.0f, radius, Path.Direction.CCW)
        canvas.drawPath(mPath, mPaint)
    }

    private fun drawRectFBorder(
        canvas: Canvas,
        borderWidth: Int,
        borderColor: Int,
        rectF: RectF,
        radii: FloatArray
    ) {
        initBorderPaint(borderWidth, borderColor)
        mPath.addRoundRect(rectF, radii, Path.Direction.CCW)
        canvas.drawPath(mPath, mPaint)
    }

    private fun initBorderPaint(borderWidth: Int, borderColor: Int) {
        mPath.reset()
        mPaint.strokeWidth = borderWidth.toFloat()
        mPaint.color = borderColor
        mPaint.style = Paint.Style.STROKE
    }

    /**
     * 计算外边框的RectF
     */
    private fun initBorderRectF() {
        if (!isCircle) {
            borderRectF[borderWidth / 2.0f, borderWidth / 2.0f, mWidth - borderWidth / 2.0f] =
                mHeight - borderWidth / 2.0f
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private fun initSrcRectF() {
        if (isCircle) {
            radius = min(mWidth, mHeight) / 2.0f
            srcRectF[mWidth / 2.0f - radius, mHeight / 2.0f - radius, mWidth / 2.0f + radius] =
                mHeight / 2.0f + radius
        } else {
            srcRectF[0f, 0f, mWidth.toFloat()] = mHeight.toFloat()
            if (isCoverSrc) {
                srcRectF = borderRectF
            }
        }
    }

    /**
     * 计算RectF的圆角半径
     */
    private fun calculateRadii() {
        if (isCircle) {
            return
        }
        if (cornerRadius > 0) {
            for (i in borderRadii.indices) {
                borderRadii[i] = cornerRadius.toFloat()
                srcRadii[i] = cornerRadius - borderWidth / 2.0f
            }
        } else {
            borderRadii[1] = cornerTopLeftRadius.toFloat()
            borderRadii[0] = borderRadii[1]
            borderRadii[3] = cornerTopRightRadius.toFloat()
            borderRadii[2] = borderRadii[3]
            borderRadii[5] = cornerBottomRightRadius.toFloat()
            borderRadii[4] = borderRadii[5]
            borderRadii[7] = cornerBottomLeftRadius.toFloat()
            borderRadii[6] = borderRadii[7]
            srcRadii[1] = cornerTopLeftRadius - borderWidth / 2.0f
            srcRadii[0] = srcRadii[1]
            srcRadii[3] = cornerTopRightRadius - borderWidth / 2.0f
            srcRadii[2] = srcRadii[3]
            srcRadii[5] = cornerBottomRightRadius - borderWidth / 2.0f
            srcRadii[4] = srcRadii[5]
            srcRadii[7] = cornerBottomLeftRadius - borderWidth / 2.0f
            srcRadii[6] = srcRadii[7]
        }
    }

    private fun calculateRadiiAndRectF(reset: Boolean) {
        if (reset) {
            cornerRadius = 0
        }
        calculateRadii()
        initBorderRectF()
        invalidate()
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private fun clearInnerBorderWidth() {
        if (!isCircle) {
            innerBorderWidth = 0
        }
    }

    fun isCoverSrc(isCoverSrc: Boolean) {
        this.isCoverSrc = isCoverSrc
        initSrcRectF()
        invalidate()
    }

    fun isCircle(isCircle: Boolean) {
        this.isCircle = isCircle
        clearInnerBorderWidth()
        initSrcRectF()
        invalidate()
    }

    fun setBorderWidth(borderWidth: Int) {
        this.borderWidth = dp2px(context, borderWidth)
        calculateRadiiAndRectF(false)
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        this.borderColor = borderColor
        invalidate()
    }

    fun setInnerBorderWidth(innerBorderWidth: Int) {
        this.innerBorderWidth = dp2px(context, innerBorderWidth)
        clearInnerBorderWidth()
        invalidate()
    }

    fun setInnerBorderColor(@ColorInt innerBorderColor: Int) {
        this.innerBorderColor = innerBorderColor
        invalidate()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = dp2px(context, cornerRadius)
        calculateRadiiAndRectF(false)
    }

    fun setCornerTopLeftRadius(cornerTopLeftRadius: Int) {
        this.cornerTopLeftRadius = dp2px(context, cornerTopLeftRadius)
        calculateRadiiAndRectF(true)
    }

    fun setCornerTopRightRadius(cornerTopRightRadius: Int) {
        this.cornerTopRightRadius = dp2px(context, cornerTopRightRadius)
        calculateRadiiAndRectF(true)
    }

    fun setCornerBottomLeftRadius(cornerBottomLeftRadius: Int) {
        this.cornerBottomLeftRadius = dp2px(context, cornerBottomLeftRadius)
        calculateRadiiAndRectF(true)
    }

    fun setCornerBottomRightRadius(cornerBottomRightRadius: Int) {
        this.cornerBottomRightRadius = dp2px(context, cornerBottomRightRadius)
        calculateRadiiAndRectF(true)
    }

    fun setMaskColor(@ColorInt maskColor: Int) {
        this.maskColor = maskColor
        invalidate()
    }

    private fun dp2px(context: Context, dipValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}