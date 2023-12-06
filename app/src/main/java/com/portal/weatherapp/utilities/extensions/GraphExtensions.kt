package com.portal.weatherapp.utilities.extensions

import android.graphics.Color
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

fun XAxis.setCustomXAxisValueFormatter(daysOfWeek: List<String>) {
    val customFormatter = object : ValueFormatter() {
        private val dayCount = daysOfWeek.size

        override fun getFormattedValue(value: Float): String {
            val dayIndex = value.toInt() % dayCount
            return daysOfWeek[dayIndex]
        }

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val dayIndex = value.toInt() % dayCount
            return daysOfWeek[dayIndex]
        }
    }
    valueFormatter = customFormatter
}

fun LineDataSet.configureLineDataSet(newColor: Int) {
    color = newColor
    lineWidth = 2f
    valueTextSize = 12f
    setDrawValues(false)
}

fun XAxis.configureXAxis(daysOfWeek: List<String>) {
    position = XAxis.XAxisPosition.BOTTOM
    labelRotationAngle = -90f
    textSize = 14f
    textColor = Color.WHITE
    gridColor = Color.WHITE
    setCustomXAxisValueFormatter(daysOfWeek)
}

fun YAxis.configureYAxis() {
    textColor = Color.WHITE
    gridColor = Color.WHITE
    textSize = 14f
}

