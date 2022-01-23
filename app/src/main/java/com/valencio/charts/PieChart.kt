package com.valencio.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valencio.lib.PieChart
import com.valencio.lib.PieChartBean
import java.math.BigDecimal

class PieChart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        setChartData()
    }

    private fun setChartData() {
        val pieChartView = findViewById<PieChart>(R.id.pieChartView)
        val list = arrayListOf<PieChartBean>()
        list.add(PieChartBean("P-123", BigDecimal(30), Color.BLUE))
        list.add(PieChartBean("N-23", BigDecimal(22), Color.GREEN))
        list.add(PieChartBean("R-197", BigDecimal(21), Color.LTGRAY))
        list.add(PieChartBean("T-486", BigDecimal(15), Color.CYAN))
        list.add(PieChartBean("R-65", BigDecimal(7), Color.MAGENTA))
        list.add(PieChartBean("SR-192", BigDecimal(5), Color.DKGRAY))
        pieChartView!!.mData = list
    }
}