package com.valencio.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valencio.lib.PieChartBean
import java.math.BigDecimal

class PieChart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
    }

    /*private fun setChartData() {
        val list = arrayListOf<PieChartBean>()
        list.add(PieChartBean("1", BigDecimal(30), Color.BLUE))
        list.add(PieChartBean("2", BigDecimal(22), Color.GREEN))
        list.add(PieChartBean("3", BigDecimal(21), Color.LTGRAY))
        list.add(PieChartBean("4", BigDecimal(15), Color.CYAN))
        list.add(PieChartBean("5", BigDecimal(7), Color.MAGENTA))
        list.add(PieChartBean("6", BigDecimal(5), Color.DKGRAY))
        pieChartView.mData = list
    }*/
}