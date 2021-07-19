package com.valencio.lib


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import java.math.BigDecimal

class BarChartView : View {

    var mData: List<ChartBean> = arrayListOf()
        set(value) {
            field = value
            mAni.cancel()
            setMeasureData()
            mAni.start()
        }

    private val mPaint by lazy {
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 1F
        paint
    }
    private val mEffectPaint by lazy {
        val paint = Paint()
        val pathEffect = DashPathEffect(floatArrayOf(8F, 8F), 1F)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1F
        paint.color = Color.parseColor("#B4BBC6")
        paint.isAntiAlias = true
        paint.pathEffect = pathEffect
        paint
    }
    private val mPath by lazy { Path() }


    private var mAniProgress: Float = 0F
    private val mAni by lazy {
        val ani = ValueAnimator.ofFloat(0F, 1F, 0.8F, 1F)
        ani.duration = 1380
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

    private var mYTextSize: Float = 20F
    private var mYTextMaxWidth: Float = 120F
    private var mYTextRightMargin: Float = 20F
    private var mYTextColor: Int = Color.parseColor("#121822")

    private var mXTextSize: Float = mYTextSize
    private var mXTextTopMargin: Float = mYTextRightMargin
    private var mXTextColor: Int = Color.parseColor("#121822")

    private var mBarTopTextSize: Float = mYTextSize
    private var mBarTopTextBottomMargin = 8F
    private var mBarTopTextColor: Int = Color.parseColor("#1B4073")

    private var mBarRadius: Float = 10F
    private var mBarWidth: Float = 20F
    private var mBarColor: Int = Color.parseColor("#2684FF")
    private var mBarSpace: Float = 20F
    private var mFormLineColor: Int = Color.parseColor("#B4BBC6")

    private var mYSpace: Int = 6
    private var mFormYBarSpace: Float = 20F

    private var mYSpaceSize: BigDecimal = BigDecimal(0)
    private var mFormTopEmpty: Float = 20F

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

    override fun onDetachedFromWindow() {
        mAni.cancel()
        super.onDetachedFromWindow()
    }

    private fun init(attrs: AttributeSet?) {
        attrs?.apply {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.BarChartView)
            mYTextSize = ta.getDimension(R.styleable.BarChartView_YTextSize, mYTextSize)
            mYTextRightMargin =
                ta.getDimension(R.styleable.BarChartView_YTextRightMargin, mYTextRightMargin)
            mYTextColor = ta.getColor(R.styleable.BarChartView_YTextColor, mYTextColor)
            mXTextSize = ta.getDimension(R.styleable.BarChartView_XTextSize, mXTextSize)
            mXTextTopMargin =
                ta.getDimension(R.styleable.BarChartView_XTextTopMargin, mXTextTopMargin)
            mXTextColor = ta.getColor(R.styleable.BarChartView_XTextColor, mXTextColor)
            mBarTopTextSize =
                ta.getDimension(R.styleable.BarChartView_BarTopTextSize, mBarTopTextSize)
            mBarTopTextBottomMargin = ta.getDimension(
                R.styleable.BarChartView_BarTopTextBottomMargin, mBarTopTextBottomMargin
            )
            mBarTopTextColor =
                ta.getColor(R.styleable.BarChartView_BarTopTextColor, mBarTopTextColor)
            mBarRadius = ta.getDimension(R.styleable.BarChartView_BarRadius, mBarRadius)
            mBarColor = ta.getColor(R.styleable.BarChartView_BarColor, mBarColor)
            mFormLineColor = ta.getColor(R.styleable.BarChartView_FormLineColor, mFormLineColor)
            ta.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setMeasureData()
    }

    private fun setMeasureData() {
        initYSpaceSize()
        mYTextMaxWidth = getStringWidth("${getMaxNum().toInt()}", mYTextSize)
        val formWidth =
            measuredWidth - (paddingLeft + paddingRight) - (mYTextMaxWidth + mYTextRightMargin)
        val temp = 1.5F
        mBarWidth = formWidth / (mData.size + (mData.size + 1) * temp)
        mBarSpace = mBarWidth * temp

        val formHeight =
            measuredHeight - (paddingTop + paddingBottom) - (mXTextSize + mXTextTopMargin) - mFormTopEmpty
        mFormYBarSpace = formHeight / mYSpace
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mData.isEmpty()) return

        drawLefYText(canvas)
        drawFormLine(canvas)
        drawFormContent(canvas)
    }

    private fun drawLefYText(canvas: Canvas) {
        mPaint.color = mYTextColor
        mPaint.textSize = mYTextSize
        val tempX = paddingLeft + mYTextMaxWidth
        val tempY = measuredHeight - paddingBottom - (mXTextSize + mXTextTopMargin)
        var x: Float
        var y: Float
        var str: String
        for (i in 0..mYSpace) {
            str = "${(mYSpaceSize * BigDecimal(i)).toInt()}"
            x = tempX - getStringWidth(str, mYTextSize)
            y = tempY - i * mFormYBarSpace + mYTextSize / 2
            canvas.drawText(str, x, y, mPaint)
        }
    }

    private fun drawFormLine(canvas: Canvas) {
        mPaint.color = mFormLineColor
        val x = paddingLeft + mYTextMaxWidth + mYTextRightMargin
        var y = measuredHeight - paddingBottom - (mXTextSize + mXTextTopMargin)

        canvas.drawLine(x, y, x, paddingTop.toFloat(), mPaint)
        canvas.drawLine(x, y, (measuredWidth - paddingRight).toFloat(), y, mPaint)

        for (i in 1..mYSpace) {
            y -= mFormYBarSpace
            val path = Path()
            path.moveTo(x, y)
            path.lineTo((measuredWidth - paddingRight).toFloat(), y)
            canvas.drawPath(path, mEffectPaint)
        }
    }

    private fun drawFormContent(canvas: Canvas) {
        val bottomTextY = (measuredHeight - paddingBottom).toFloat()
        val barEndY = bottomTextY - mXTextSize - mXTextTopMargin
        val formContentStartX = paddingLeft + mYTextMaxWidth + mYTextRightMargin
        val mFormBarHelfWidth = mBarWidth / 2F

        var barCenterX: Float
        var barHeight: Float
        var str: String
        var left: Float
        var top: Float
        var right: Float
        var tempRadius: Float
        for (i in mData.indices) {
            barCenterX = formContentStartX + mBarSpace * (i + 1) + ((i + 0.5F) * mBarWidth)

            mPaint.textSize = mXTextSize
            mPaint.color = mXTextColor
            canvas.drawText(
                mData[i].type, barCenterX - getStringWidth(mData[i].type, mXTextSize) / 2,
                bottomTextY, mPaint
            )


            mPaint.color = mBarColor
            barHeight = (mData[i].data * mFormYBarSpace.toBigDecimal() / mYSpaceSize).toFloat()
            barHeight *= mAniProgress
            left = barCenterX - mFormBarHelfWidth
            top = barEndY - barHeight
            right = barCenterX + mFormBarHelfWidth
            tempRadius = if (mBarWidth / 2 < mBarRadius) mBarWidth / 2 else mBarRadius
            tempRadius = if (barHeight > tempRadius) tempRadius else barHeight
            mPath.reset()
            mPath.moveTo(left, barEndY)
            mPath.lineTo(left, top + tempRadius)
            mPath.quadTo(left, top, left + tempRadius, top)
            mPath.lineTo(right - tempRadius, top)
            mPath.quadTo(right, top, right, top + tempRadius)
            mPath.lineTo(right, barEndY)
            mPath.close()
            canvas.drawPath(mPath, mPaint)
            mPaint.textSize = mBarTopTextSize
            mPaint.color = mBarTopTextColor
            str = mData[i].data.toString()
            canvas.drawText(
                str, barCenterX - getStringWidth(str, mBarTopTextSize) / 2,
                barEndY - barHeight - mBarTopTextBottomMargin, mPaint
            )
        }
    }

    private fun getMaxNum(): BigDecimal {
        var max = BigDecimal(0)
        if (mData.isEmpty()) return max
        mData.forEach {
            if (max < it.data) max = it.data
        }
        return max
    }


    private fun getStringWidth(string: String, textSize: Float): Float {
        mPaint.textSize = textSize
        return mPaint.measureText(string)
    }


    private fun initYSpaceSize() {
        var max = getMaxNum()
        var temp = BigDecimal(1)

        val temp2 = BigDecimal(50)
        val temp3 = BigDecimal(10)
        while (max >= temp2) {
            max /= temp3
            temp *= temp3
        }

        when {
            max >= BigDecimal(25) -> temp *= BigDecimal(5)
            max >= BigDecimal(15) -> temp *= BigDecimal(3)
            max >= BigDecimal(8) -> temp *= BigDecimal(2)
        }

        mYSpaceSize = temp
        mYSpace = (getMaxNum() / mYSpaceSize).toInt()
        if (max % mYSpaceSize > BigDecimal(0)) mYSpace += 1
    }
}