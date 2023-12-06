package com.portal.weatherapp.ui.graph

import androidx.lifecycle.ViewModel
import com.portal.weatherapp.R
import com.portal.weatherapp.data.model.GraphItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor() : ViewModel() {

    private val _mockList = MutableStateFlow<List<Pair<String, Float>>>(emptyList())
    val mockList = _mockList.asStateFlow()

    private val _graphList = MutableStateFlow<List<GraphItem>>(mutableListOf())
    val graphList = _graphList.asStateFlow()


    init {
        val hours = arrayOf("12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM")

        val graphItems = mutableListOf<GraphItem>()

        for (i in 0 until 24) {
            val hour = hours[i]
            val temperature = (10..30).random().toString() + "°C"
            val graphItem = GraphItem(hour, R.drawable.ic_rain_sun, temperature)
            graphItems.add(graphItem)
        }

        setList(graphItems)
    }
    private fun setList(list: List<GraphItem>) {
        _graphList.value = list
    }


}