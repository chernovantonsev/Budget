package ru.antonc.budget.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.MainActivity
import javax.inject.Inject

open class BaseFragment : Fragment(), Injectable, OnBackPressedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is MainActivity)
            (activity as MainActivity).setOnBackPressedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (activity is MainActivity)
            (activity as MainActivity).removeOnBakPressedListener(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}