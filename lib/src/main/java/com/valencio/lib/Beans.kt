package com.valencio.lib

import androidx.annotation.ColorInt
import java.math.BigDecimal

data class ChartBean(
    val type: String,
    val data: BigDecimal
)

data class PieChartBean(
    val type: String,
    val data: BigDecimal,
    @ColorInt val color: Int
)