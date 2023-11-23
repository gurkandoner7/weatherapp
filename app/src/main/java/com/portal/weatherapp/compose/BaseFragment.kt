package com.portal.weatherapp.compose

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(bindingLayout: Int) : Fragment(bindingLayout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVariables()
        initUI(savedInstanceState)
    }

    abstract fun observeVariables()
    abstract fun initUI(savedInstanceState: Bundle?)
}