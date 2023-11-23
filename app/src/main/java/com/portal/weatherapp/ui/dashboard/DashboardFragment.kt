package com.portal.weatherapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)


    override fun observeVariables() {
    }

    override fun initUI(savedInstanceState: Bundle?) {
    }


}