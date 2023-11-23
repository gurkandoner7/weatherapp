package com.portal.weatherapp.ui.notifications

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
import com.portal.weatherapp.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment(R.layout.fragment_notifications) {

    private val homeViewModel: NotificationsViewModel by activityViewModels()
    private val binding: FragmentNotificationsBinding by viewBinding(FragmentNotificationsBinding::bind)

    override fun observeVariables() {

    }

    override fun initUI(savedInstanceState: Bundle?) {

    }


}