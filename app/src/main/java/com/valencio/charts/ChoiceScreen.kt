package com.valencio.charts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoiceScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_screen)
        onClickListeners()
    }

    private fun onClickListeners() {
        val barGraph = findViewById<Button>(R.id.barGraph)
        barGraph.setOnClickListener {
            val barGraphActivity = Intent(applicationContext, BarGraph::class.java)
            startActivity(barGraphActivity)
        }

        val pieChart = findViewById<Button>(R.id.pieChart)
        pieChart.setOnClickListener {
            val pieChartActivity = Intent(applicationContext, PieChart::class.java)
            startActivity(pieChartActivity)
        }
    }
}