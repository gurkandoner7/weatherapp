package com.portal.weatherapp.ui.location

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment(R.layout.fragment_location) {

    private val locationViewModel: LocationViewModel by activityViewModels()

    private val binding: FragmentLocationBinding by viewBinding(FragmentLocationBinding::bind)


    override fun observeVariables() {
    }

    override fun initUI(savedInstanceState: Bundle?) {
    }


}