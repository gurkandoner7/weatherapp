package com.portal.weatherapp.ui.graph

import androidx.lifecycle.ViewModel
import com.portal.weatherapp.data.model.GraphItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor() : ViewModel() {

    private val _mockList = MutableStateFlow<List<Pair<String, Float>>>(emptyList())
    val mockList = _mockList.asStateFlow()

    private val _graphList = MutableStateFlow<MutableList<GraphItem>>(mutableListOf())
    val graphList = _graphList.asStateFlow()


}