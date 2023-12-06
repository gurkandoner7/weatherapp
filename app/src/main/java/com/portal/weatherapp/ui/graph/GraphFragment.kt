package com.portal.weatherapp.ui.graph

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.portal.Graphapp.ui.graph.adapter.GraphAdapter
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentGraphBinding
import com.portal.weatherapp.utilities.extensions.configureLineDataSet
import com.portal.weatherapp.utilities.extensions.configureXAxis
import com.portal.weatherapp.utilities.extensions.configureYAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class GraphFragment : BaseFragment(R.layout.fragment_graph) {

    private val graphViewModel: GraphViewModel by activityViewModels()
    private val binding: FragmentGraphBinding by viewBinding(FragmentGraphBinding::bind)
    private lateinit var adapter: GraphAdapter

    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                graphViewModel.graphList.collect {
                    adapter.updateItems(it)
                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        adapter = GraphAdapter(requireContext())
        binding.rvHourly.adapter = adapter
        setupLineChart()
        setLineChartData()
        binding.btnSignOut.setButtonOnClick {
            signOut()
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_navigation_graph_to_loginFragment)
    }

    private fun setupLineChart() {
        binding.lineChart.apply {
            setTouchEnabled(true)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                private var marker: MarkerView? = null

                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    if (e != null) {
                        val selectedXValue =
                            binding.lineChart.xAxis.valueFormatter?.getFormattedValue(
                                e.x,
                                binding.lineChart.xAxis
                            ) ?: ""
                        binding.tvSelectedDay.setText(selectedXValue)
                    }
                }

                override fun onNothingSelected() {
                    marker = null
                    invalidate()
                }
            })
        }

    }

    private fun setLineChartData() {
        val entries = listOf(
            Entry(0f, 17.1f),
            Entry(1f, 18.6f),
            Entry(2f, 19.2f),
            Entry(3f, 18.4f),
            Entry(4f, 21.5f),
            Entry(5f, 21.7f),
            Entry(6f, 19.2f)
        )

        val dataSet = LineDataSet(entries, "Temperature").apply {
            configureLineDataSet(ContextCompat.getColor(requireContext(), R.color.bgGraphColor))
        }

        val daysOfWeek = mutableListOf<String>().apply {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
            for (i in 0 until 7) {
                if (i != 0) {
                    calendar.add(Calendar.DAY_OF_WEEK, 1)
                }
                add(dateFormat.format(calendar.time))
            }
        }
        binding.tvSelectedDay.setText(daysOfWeek.first())
        binding.lineChart.apply {
            data = LineData(dataSet)
            invalidate()

            xAxis.apply {
                configureXAxis(daysOfWeek)
            }

            axisRight.isEnabled = false
            axisLeft.apply {
                configureYAxis()
            }

        }
    }
}