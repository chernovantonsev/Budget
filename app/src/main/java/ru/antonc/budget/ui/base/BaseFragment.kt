package ru.antonc.budget.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.main.MainActivity
import ru.antonc.budget.ui.main.removeOnBakPressedListener
import ru.antonc.budget.ui.main.setOnBackPressedListener
import javax.inject.Inject

open class BaseFragment(private val isNeedOverrideBackPressed: Boolean = true) : Fragment(),
    Injectable,
    OnBackPressedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNeedOverrideBackPressed)
            addBackPressedListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (isNeedOverrideBackPressed)
            removeBackPressedListener()
    }

    fun addBackPressedListener() {
        if (activity is MainActivity)
            (activity as MainActivity).setOnBackPressedListener(this)
    }

    fun removeBackPressedListener() {
        if (activity is MainActivity)
            (activity as MainActivity).removeOnBakPressedListener(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}