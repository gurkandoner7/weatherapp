package com.portal.weatherapp.ui.home

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                homeViewModel.weatherResponse.collect{
                    println(it.base)
                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        homeViewModel.setLatAndLon(lat = 41.0082, lon = 28.9784)
        homeViewModel.getCurrentWeather()
    }

}