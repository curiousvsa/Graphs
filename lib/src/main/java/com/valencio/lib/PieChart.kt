package com.valencio.lib

import android.view.View
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import kotlin.math.cos
import kotlin.math.sin

class PieChart : View {

    var mData: List<PieChartBean> = arrayListOf()
        set(value) {
            field = value
            mAni.cancel()
            calculateProportion()
            mAni.start()
        }
    private val mProportion = mutableListOf<Float>()

    private val mPaint by lazy {
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 1F
        paint
    }

    private var mAniProgress: Float = 0F
    private val mAni by lazy {
        val ani = ValueAnimator.ofFloat(0F, 1F)
        ani.duration = 800
        ani.startDelay = 100
        ani.repeatCount = 0
        ani.interpolator = AccelerateDecelerateInterpolator()
        ani.addUpdateListener {
            val value = it.animatedValue
            if (value is Float) {
                mAniProgress = value
                postInvalidate()
            }
        }
        ani
    }

    private var mPieRadius: Float = 320F
    private var mPieCenterPoint: PointF = PointF(0F, 0F)
    private var mPieDrawRectF: RectF = RectF(0F, 0F, 0F, 0F)

    private var mTextSize: Float = 30F
    private var mTextTopMargin: Float = 20F
    private var mPieTextSize: Float = 18F
    private var mPieTextColor: Int = Color.BLACK

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        attrs?.apply {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PieChartView)
            mPieRadius = ta.getDimension(R.styleable.PieChartView_PieRadius, mPieRadius)
            mTextSize = ta.getDimension(R.styleable.PieChartView_TextSize, mTextSize)
            mTextTopMargin = ta.getDimension(R.styleable.PieChartView_TextTopMargin, mTextTopMargin)
            mPieTextSize = ta.getDimension(R.styleable.PieChartView_PieTextSize, mPieTextSize)
            mPieTextColor = ta.getColor(R.styleable.PieChartView_PieTextColor, mPieTextColor)
            ta.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setMeasureData()
    }

    private fun setMeasureData() {
        val maxStrWidth = getMaxStrWidth()
        mPieCenterPoint = PointF(
            (measuredWidth - (paddingLeft + paddingRight + maxStrWidth)) / 2 + (paddingLeft + maxStrWidth),
            (measuredHeight - (paddingTop + paddingBottom)).toFloat() / 2 + paddingTop
        )
        mPieDrawRectF = RectF(
            mPieCenterPoint.x - mPieRadius, mPieCenterPoint.y - mPieRadius,
            mPieCenterPoint.x + mPieRadius, mPieCenterPoint.y + mPieRadius
        )
    }

    private fun calculateProportion() {
        val total = mData.fold(BigDecimal(0)) { tempTotal, next ->
            tempTotal + next.data
        }
        mProportion.clear()
        var tempTotal = 0F
        var tempDe: Float
        val totalDe = BigDecimal(360)
        mData.withIndex().forEach {
            if (it.index == mData.size - 1 && it.index > 0) {
                mProportion.add(360F - tempTotal)
            } else {
                tempDe = (it.value.data * totalDe / total).toFloat()
                mProportion.add(tempDe)
                tempTotal += tempDe
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mData.isEmpty()) return

        var startAngle = -90F
        var angle: Float
        mData.withIndex().forEach {
            angle = mProportion[it.index] * mAniProgress
            mPaint.color = it.value.color
            canvas.drawArc(mPieDrawRectF, startAngle, angle, true, mPaint)
            startAngle += angle
        }

        if (mAniProgress >= 1) {
            startAngle = -90F
            mPaint.color = mPieTextColor
            mPaint.textSize = mPieTextSize
            var textAngle: Float
            var textX: Float
            var textY: Float
            mData.withIndex().forEach {
                angle = mProportion[it.index] * mAniProgress
                textAngle = startAngle + angle / 2
                textX =
                    mPieCenterPoint.x + (mPieRadius * cos(textAngle * Math.PI / 180) * 3 / 5).toFloat()
                textX -= getStringWidth("${it.value.data}%", mPieTextSize) / 2
                textY =
                    mPieCenterPoint.y + (mPieRadius * sin(textAngle * Math.PI / 180) * 3 / 5).toFloat()
                textY -= mPieTextSize / 2
                canvas.drawText("${it.value.data}%", textX, textY, mPaint)
                startAngle += angle
            }

            textY = paddingLeft.toFloat() + mTextTopMargin
            mPaint.textSize = mTextSize
            mData.forEach {
                mPaint.color = it.color
                canvas.drawText("${it.type}: ${it.data}%", paddingLeft.toFloat(), textY, mPaint)
                textY += mTextSize + mTextTopMargin
            }
        }
    }

    private fun getStringWidth(string: String, textSize: Float): Float {
        mPaint.textSize = textSize
        return mPaint.measureText(string)
    }

    private fun getMaxStrWidth(): Float {
        var str = ""
        mData.forEach {
            if (it.type.length + it.data.toString().length + 3 > str.length) {
                str = "${it.type}: ${it.data}%"
            }
        }
        return if (str.isEmpty()) 0F else getStringWidth(str, mTextSize)
    }
}

