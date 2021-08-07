package com.valencio.charts

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.valencio.lib.BarChartView
import com.valencio.lib.ChartBean
import java.math.BigDecimal
import java.util.*

class MainActivity : AppCompatActivity() {

    private var resetClicked: Boolean = false
    private val random: Int = Random().nextInt(61) + 20 // [0, 60] + 20 => [20, 80]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resetData = findViewById<TextView>(R.id.resetData)
        resetData.setOnClickListener {
            if (resetClicked) {
                resetClicked = false
                setChartData()
            } else {
                resetClicked = true
                resetChartData()
            }
        }
        setChartData()

    }


    private fun setChartData() {
        val list = arrayListOf<ChartBean>()
        val barChartView = findViewById<BarChartView>(R.id.barChartView)
        for (items in 0 until 2) {
            val value = random + items
            list.add(ChartBean("Loop Tasks ", BigDecimal(value)))
        }
        list.add(ChartBean("Product", BigDecimal(42000)))
        list.add(ChartBean("Product", BigDecimal(39000)))
        list.add(ChartBean("Product", BigDecimal(28800)))
        list.add(ChartBean("Sales", BigDecimal(20000)))
        list.add(ChartBean("HR", BigDecimal(1500)))
        list.add(ChartBean("Expenses", BigDecimal(2000)))
        list.add(ChartBean("Resources", BigDecimal(10000)))
        list.add(ChartBean("Completed", BigDecimal(2500)))
        list.add(ChartBean("Pending", BigDecimal(18500)))
        barChartView.mData = list
    }

    private fun resetChartData() {
        val list = arrayListOf<ChartBean>()
        val barChartView = findViewById<BarChartView>(R.id.barChartView)
        for (items in 0 until 2) {
            val value = random + items
            list.add(ChartBean("Loop Tasks 1", BigDecimal(value)))
        }
        list.add(ChartBean("1", BigDecimal(4400)))
        list.add(ChartBean("Product", BigDecimal(28800)))
        list.add(ChartBean("Sales", BigDecimal(20000)))
        list.add(ChartBean("HR", BigDecimal(1500)))
        list.add(ChartBean("Expenses", BigDecimal(2000)))
        list.add(ChartBean("Resources", BigDecimal(10000)))
        list.add(ChartBean("Completed", BigDecimal(2500)))
        list.add(ChartBean("Pending", BigDecimal(18500)))
        barChartView.mData = list
    }
}
